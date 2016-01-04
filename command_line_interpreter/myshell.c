/* GEORGI NIKOLOV - 140367900*/
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>

/**Declaration of welcome screen**/
void welcome_Screen()
{
    printf("\n-------------------------------------------------\n");
    printf("\tWelcome to LAME-shell \n");
    printf("\tLong Agonizing Much Effort - shell\n");
    printf("\tThis is LAME-SHELL with process id: %d\n", (int) getpid());
    printf("\tType in ""help"" for more information about the shell\n");
    printf("-------------------------------------------------\n");
    printf("\n\n");
}

/**Declaration of help function*/
void help()
{
    printf("/**********************LAME SHELL***********************************/\n");
    printf("This shell has only basic features, but can execute most of UNIX commands\n");
    printf("It is build with education purposes, feel free to share it or correct it.\n");
    printf("Supports build in commands, cd, exit, help\n");
    printf(" Terminates (involuntarily) the foreground process when user presses ^C and \nreturn back to the shell\n");

}
/*************************************@launch_job*************************************/
/*********@param **argument - pointer to pointer containing arguments*****************/
/*********@purpose Points to tokenized arguments and forks a process******************/
/**Usage based on the user input executes command with the arguments for it(if any)**/


int launch_job(char **tokenizer)
{
    pid_t process, wait_process;
    int status;

    process = fork();

    if(process==0)
    {
//invoke child process
        if(execvp(tokenizer[0],tokenizer)==-1)
        {
            perror("Failure to create child process");
        }
        exit(EXIT_FAILURE);
    }
    else if (process<0)
    {
        //Error while forking process
        perror("Failure to fork process");
    }
    else
    {
        //Create Parent process
        do
        {
            wait_process = waitpid(process, &status, WUNTRACED );
        }
        while (!WIFSIGNALED(status)&& !WIFEXITED(status));
    }
    return 1;

}
/**Implementation of build in commands, their functionality**/
/**is implemented whithin the if statements only help command*/
/**comes from an external function.***************************/
/**Once all statemens are checked launch_job is returned******/
int check_builtins_execute(char **tokenizer)
{
    if (tokenizer[0] == NULL)
    {
        // Used to prevent core dump error if user enters an empty line
        return 1;
    }
    if(strcmp("exit",tokenizer[0])==0)
    {
        exit(EXIT_SUCCESS);
    }
    else if(strcmp("cd",tokenizer[0])==0)
    {
        if(tokenizer[1]==NULL)
        {
            fprintf(stderr,"Command CD must have argument\n");
        }
        else
        {
            if(chdir(tokenizer[1])!=0)
            {
                perror("Bad path");
            }
        }
        return 1;
    }
    else if(strcmp("help",tokenizer[0])==0)
    {
        help();
        return 1;
    }
    return launch_job(tokenizer);
}

/****************************@define********************************/
/**@TOK_BS - Default size of buffer size used in the input tokenizer*/
/**@TOK_DELIMS - Delimeters used in input tokenizer *****************/
#define TOK_BS 32
#define TOK_DELIMS " \n\a\t\r"

/**@param - user_command********************************
***@purpose : Splits input into tokens and returns them***
***@return tokens*****************************************/
char **tokenize_input(char *user_command)
{
    int buffer_size = TOK_BS, pos= 0;
    char **tokens = malloc(buffer_size * sizeof(char*));
    char *token;

    while((token =strtok_r(user_command, TOK_DELIMS, &user_command)))
    {
        if (!tokens)
        {
            fprintf(stderr, "Memory allocation fault\n");
            exit(EXIT_FAILURE);
        }
        tokens[pos]=token;
        pos++;
        // printf("%s\n", token);

        /**Reallocate buffer size if not enough**/
        if(buffer_size<=pos)
        {
            buffer_size+=TOK_BS;
            tokens =realloc(tokens, buffer_size*sizeof(char*));
            if (!tokens)
            {
                fprintf(stderr, "Memory allocation fault\n");
                exit(EXIT_FAILURE);
            }
        }

        // printf("token:%s\n", token);

        token = strtok(NULL, TOK_DELIMS);

    }
    tokens[pos] = NULL;
    return tokens;
}




/****@params - void        **********************
**@purpose Takes input from keyboard, reads the***
***** whole line, in case of EOL reset arr string*
**@return user_command -  input from keyboard*/

char* input_handler()
{

    char* user_command = NULL;
    size_t len= 0;
    char read= '\0';



    /**Check if EOF reached, print perror if read not equal to EOF return command**/
    read = getline(&user_command, &len, stdin);
    /* Bug on Minix, couldn't fix it, tried my best, maybe signals here are working differently than*/
    /*In Linux, because it was working there, when EOF occurs /ctrl+D/ the shell will fall in an ***/
    /* infinite loop printing the prompt*/

    if(read!=EOF)
    {
        return user_command;
    }

    if(read==EOF)
    {
        perror("EOF");
    }
    return NULL;

}

/**Declaration of getlogin used as promt for the shell*/
char *getlogin(void);

/**@handle_signal - used for our imlementation of CTRL+C*/

typedef void (*sighandler_type)(int);

void handle_signal(int ctrl_signal)
{
    printf("\n");
    fflush(stdout);

}

/**Main method to initialise shell**/
int main()
{
    int status;
    char* get_input;
    char** tokenizer;

    signal(SIGINT, SIG_IGN);//Ignore signal
    signal(SIGINT, handle_signal); //Use custom signal
    welcome_Screen();
    do
    {

        printf("%s@%s%s","shell" ,getlogin(),":");
        get_input = input_handler();
        tokenizer = tokenize_input(get_input);
        status = check_builtins_execute(tokenizer);
        free(get_input);
        free(tokenizer);
    }
    while(status);

    exit(EXIT_SUCCESS);
}


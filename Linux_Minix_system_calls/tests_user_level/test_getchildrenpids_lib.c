#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char **argv){
  if (argc<2)
  exit(EXIT_FAILURE); //EXit program if not enough parameters are given
  
  pid_t get_pid=atoi(argv[1]);
  int get_nchildren=atoi(argv[2]);
  pid_t children_array[get_nchildren];
  
  
  /**chilren array is hardcoded on purpose**/
 
  

 
int result =  getchildpids(get_pid, get_nchildren, children_array);

for(int i = 0; i<result; i++)
{
	printf("%d Index --------- Child pid %u\n", i,children_array[i] );
}


}
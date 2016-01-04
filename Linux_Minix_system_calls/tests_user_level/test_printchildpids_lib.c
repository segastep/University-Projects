#include <unistd.h>
#include <stdlib.h>

int main(int argc, char **argv) {
    if (argc < 2)
        exit(1);    // expecting at least 1 integer parameter to test program
        
    int i = atoi(argv[1]);
    
    printchildpids(i);
}
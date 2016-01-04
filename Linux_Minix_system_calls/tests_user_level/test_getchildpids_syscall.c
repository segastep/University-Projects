#include <lib.h> // _syscall and message
#include <stdio.h>
#include <stdlib.h> //atoi&

int main(int argc, char **argv){
  if (argc<3)
  exit(EXIT_FAILURE); //EXit program if not enough parameters are given
  
  int pid=atoi(argv[1]);
  int nchildren = atoi(argv[2]);
  pid_t arr_test[nchildren];
  
  message m; //Message to pass parameters to the syscall
  m.m1_i1 = pid; //Set message to i
  m.m1_i2 = nchildren;
  m.m1_p1 = (char*) arr_test;
  
 
  
  _syscall(PM_PROC_NR, GETCHILDPIDS , &m); // pass message to the call 
  
  for(int i=0; i<nchildren; i++)
  {
  	printf("%dPID children: %u\n" , i , arr_test[i]);
  }

}
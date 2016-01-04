#include <lib.h> // _syscall and message
#include <stdio.h>
#include <stdlib.h> //atoi&

int main(int argc, char **argv){
  if (argc<2)
  exit(EXIT_FAILURE); //EXit program if not enough parameters are given
  
  int i=atoi(argv[1]);
  
  message m; //Message to pass parameters to the syscall
  m.m1_i1 = i; //Set message to i
  
  _syscall(PM_PROC_NR, GETNCHILDPIDS , &m); // pass message to the call 

}
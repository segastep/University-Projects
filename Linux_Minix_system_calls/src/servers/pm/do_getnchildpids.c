#include <stdio.h>
#include "pm.h"
#include "mproc.h"
#include <errno.h>
#include <unistd.h>


int do_getnchildpids()
{
	int input_pid = m_in.m1_i1;
	int nchildren = 0;
	
	if(input_pid<1)
	{
	  errno = EINVAL;
	  perror("Syscall: PID Must be greater than 1 and of type int");
	  return -1;
	}
	
	printf("Getting number of children processes of process with PID : %d\n", input_pid);
	for(int i=0; i<NR_PROCS; i++)
	{
		int parent_index = mproc[i].mp_parent; //Get the index of current process loop has reached
		int parent_pid = mproc[parent_index].mp_pid; // Get the PID of the process
		
		//Check if the input process is found in the PM table when found and if has PID >0 execute the code in brackets 
		//The if statement below basically gets all the children PIDs of the parent process selected by user
		if(parent_pid==input_pid && (input_pid>0)) 
		{
			int child_pid = mproc[i].mp_pid;
			
			if(child_pid>0) //Check if child PID is valid, 0 pids must be ignored 
			{
				++nchildren;
				
			}
		} 
		
	}
	
	printf("Process with PID: %d , has %d child processes\n", input_pid, nchildren);
	return nchildren;
	
}
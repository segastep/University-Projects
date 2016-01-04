#include <stdio.h>
#include "pm.h"
#include "mproc.h"
#include <errno.h>

int do_printchildpids()
{
	int input_pid = m_in.m1_i1;
	
	if(input_pid<1)
	{
	  errno = EINVAL;
	  perror("Syscall: PID Must be greater than 1 and of type int");
	  return -1;
	}

	printf("Searching for children of process: %d\n", input_pid);
	for(int i=0; i<NR_PROCS; i++)
	{
		int parent_index = mproc[i].mp_parent;
		int parent_pid = mproc[parent_index].mp_pid;
		
		if(parent_pid==input_pid && (input_pid>0))
		{
			int child_pid = mproc[i].mp_pid;
			
			if(child_pid>0) 
			{
				printf("%d\n", child_pid);
			}
		} 
	}
	return 0;
}
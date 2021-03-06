#include <lib.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>



int getnchildren(pid_t pid)
{
message m; //message to pass parameters to a sys call

if(pid<1)
{
errno = EINVAL;
perror("PID must be greater than 1 and of type int");
return -1;
}else if (pid>=1)
{
	m.m1_i1 = pid;
	return _syscall(PM_PROC_NR, GETNCHILDPIDS, &m);
	
}
 return 0;
}
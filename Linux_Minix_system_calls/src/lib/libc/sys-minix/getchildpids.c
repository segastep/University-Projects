#include <lib.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>

int getchildpids(pid_t pid, int nchildren, pid_t *childpids)
{
message m; //message to pass parameters to a sys call

if(pid<1)
{
errno = EINVAL;
perror("PID must be greater than 1 and of type int");
return -1;
}

if(nchildren<1)
{
	errno = EINVAL;
	perror("Number of children must be greater than one and of type int");
	return -1;
}

if (pid>=1 && nchildren>=1)
{
	m.m1_i1 = pid;
  	m.m1_i2 = nchildren;
  	m.m1_p1 = (char*) childpids;
	return _syscall(PM_PROC_NR, GETCHILDPIDS, &m);
}
 return 0;
 
}
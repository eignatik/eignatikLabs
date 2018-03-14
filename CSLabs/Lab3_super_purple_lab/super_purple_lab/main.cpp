#include <windows.h>
#include <stdio.h>
#include <omp.h>
#include <iostream>
#include <conio.h>

#define LOOP_SIZE 10

using namespace std;

int func(const int& sas)
{
	for (int i = 0; i<10000; i++)
	{
		rand();
	}
	return sas;
}
void main(void)
{	//Однопоточное выполнение
	/*int time1 = GetTickCount();
	for (int i = 0; i < LOOP_SIZE; ++i)
		func();
	int time2 = GetTickCount();
	time2 = time2 - time1;
	cout << "TIME 1: " << time2 << endl;*/

	//Выполнение в два потока
	int time1 = GetTickCount();


	omp_set_num_threads(2);
	//#pragma omp parallel for

	/*int wtf1 = 0;
#pragma omp parallel for
{
	for (int i = 0; i < LOOP_SIZE; ++i)
	{
		//cout << i << endl;
		wtf1--;
		printf("one   %d\n", i);
		printf("two   %d\n", i);
		printf("three %d\n", i);
		wtf1 *= 3;
		wtf1++;
		func();
	}
}

cout << "WTF" << wtf1 << endl;
_getch();



#pragma omp parallel
	int time2 = GetTickCount();
	time2 = time2 - time1;
	cout << "TIME 2: " << time2 << endl;

	time1 = GetTickCount();
#pragma omp parallel
#pragma omp sections nowait
#pragma omp section
	for (int i = 0; i < LOOP_SIZE; ++i)
	{
		cout << i << endl;
		func();
	}
	time2 = GetTickCount();
	time2 = time2 - time1;
	cout << "TIME 3: " << time2 << endl;
	*/

/*#pragma omp parallel
	{
		for (int i = 0; i < LOOP_SIZE; ++i)
		{
			printf("%d\n", func(1));
			printf("%d\n", func(2));
		}
	}

	_getch();*/
	/*

	//parallel
	#pragma omp parallel
	{
		#pragma omp for  
		for (int i = 0; i < LOOP_SIZE; ++i)
		{
			printf("%d\n", func(1));
			printf("%d\n", func(2));
		}
	}
	_getch();


	//master
	int n;

#pragma omp parallel private(n) 
	{
		n = 1;

#pragma omp master 
		{
			n = 2;
		}

		printf("First n: %d\n", n);

#pragma omp barrier 

#pragma omp master 
		{
			n = 3;
		}

		printf("Second n: %d\n", n);
	}
	_getch();

	//atomic
	int count = 0;

#pragma omp parallel 
	{
#pragma omp atomic 
		count++;
	}

	printf("Count thread: %d\n", count);
	_getch();
	*/

omp_lock_t lock;
int n;
omp_init_lock(&lock);
/*
#pragma omp parallel private (n)
{
	n = omp_get_thread_num();
	#pragma omp sections
	{
		/*omp_set_lock(&lock);
		printf("%d\n", func(1));
		omp_unset_lock(&lock);

		#pragma omp section
		printf("%d\n", omp_test_lock(&lock));

		#pragma omp section
		printf("sec\n");
	}
}
omp_destroy_lock(&lock);

*/

#pragma omp parallel sections
{
#pragma omp section
{
	omp_set_lock(&lock);

	printf("id = %d, \n", omp_get_thread_num());
	omp_unset_lock(&lock);
}

#pragma omp section
{
	while (!omp_test_lock(&lock)) {
		printf("id = %d, \n", omp_get_thread_num());
	}
}
}
omp_destroy_lock(&lock);

_getch();

}
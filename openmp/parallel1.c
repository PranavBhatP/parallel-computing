#include <stdio.h>
#include <omp.h>

void task1() { printf("Task1 done by thread %d\n", omp_get_thread_num()); }
void task2() { printf("Task2 done by thread %d\n", omp_get_thread_num()); }
void task3() { printf("Task3 done by thread %d\n", omp_get_thread_num()); }

int main() {
    #pragma omp parallel num_threads(4)
    {
        #pragma omp sections
        {
            #pragma omp section
            task1();

            #pragma omp section
            task2();

            #pragma omp section
            task3();
        }
    }
    return 0;
}

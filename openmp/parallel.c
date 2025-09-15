#include<stdio.h>
#include<omp.h>


int main(){
    #pragma omp parallel
    {
        int id = omp_get_thread_num();
        int total = omp_get_num_threads();
        printf("Thread %d of %d\n", id, total);

    }
    return 0;
}
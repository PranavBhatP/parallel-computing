#include<stdio.h>
#include<omp.h>

int main(){
    long sum = 0;
    long n = 1000000;
    #pragma omp parallel for reduction(+:sum)
    for(long i = 0; i < n; i++){
        sum += i;
    }
    printf("Sum is %ld\n", sum);
    return 0;
}
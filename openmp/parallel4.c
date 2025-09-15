#include <stdio.h>
#include <omp.h>

int main() {
    int n = 10;
    int sum = 0;

    #pragma omp parallel
    {
        // Each thread will do a portion of the loop
        #pragma omp for reduction(+:sum)
        for (int i = 0; i < n; i++) {
            sum += i * i;
        }

        // Only ONE thread prints (others skip this block)
        #pragma omp single
        {
            printf("Sum computed by %d threads = %d\n",
                   omp_get_num_threads(), sum);
        }
    }
    return 0;
}

#include <stdio.h>
#include <omp.h>

int main() {
    omp_set_nested(1);  // enable nested parallelism

    #pragma omp parallel num_threads(4)
    {
        int outer_id = omp_get_thread_num();
        int outer_n  = omp_get_num_threads();
        printf("Outer thread %d of %d\n", outer_id, outer_n);

        #pragma omp parallel num_threads(3)
        {
            int inner_id = omp_get_thread_num();
            int inner_n  = omp_get_num_threads();
            printf("   Inner thread %d of %d (inside outer %d)\n",
                   inner_id, inner_n, outer_id);
        }
    }
    return 0;
}

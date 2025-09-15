## Java multithreading notes

Instruction-Level Parallelism (ILP):
ILP refers to a processor’s ability to execute multiple instructions simultaneously by overlapping their execution. This is achieved by identifying independent instructions and scheduling them to run in parallel, improving CPU throughput.

SIMD (Single Instruction, Multiple Data):
SIMD is a parallel computing architecture where a single instruction operates on multiple data points at once. It is commonly used in multimedia, graphics, and scientific applications to accelerate tasks like vector and matrix operations.

Multithreading (Within a Core):
Multithreading allows a single CPU core to execute multiple threads concurrently, often by rapidly switching between them or using hardware support for multiple thread contexts. This improves resource utilization and responsiveness, especially in workloads with frequent stalls.

Superscalar Execution:
Superscalar execution is a processor design that enables the execution of more than one instruction per clock cycle by providing multiple execution units. The CPU fetches, decodes, and dispatches several instructions simultaneously, increasing overall performance.
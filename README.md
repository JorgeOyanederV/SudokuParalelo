# SudokuParalelo
Resumen: Se paralelizo el codigo de resolucion de sudoku con bracktracking
A continuacion se ven los resultados de cada test.

## Test 1 Hilo
Tiempo Promedio: 0.327 s
SpeedUp: 1
Eficiencia: 1

## Test 2 Hilos
Tiempo Promedio: 0.433 s
SpeedUp: 0.775
Eficiencia: 0.378

## Test 3 Hilos
Tiempo Promedio: 0.527 s
SpeedUp: 0.620
Eficiencia: 0.207

## Test 4 hilos
Tiempo Promedio: 0.542 s
SpeedUp: 0.603
Eficiencia: 0.151

## Test 5 Hilos
Tiempo Promedio: 0.520 s
SpeedUp: 0.629
Eficiencia: 0.126

## Test 6 Hilos
Tiempo Promedio: 0.524 s
SpeedUp: 0.624
Eficiencia: 0.104

A partir de los resultados podemos ver que la SpeedUp no mejora a 
partir de 3 hilos o mas, esto era de esperarse ya que el codigo permite 
un maximo de 3 hilos y espera que terminen estos 3 tareas para poder continuar.

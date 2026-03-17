import java.util.Random;

/** 
 * MIT License
 *
 * Copyright(c) 2024-255 João Caram <caram@pucminas.br>
 *                       Eveline Alonso Veloso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random(42);
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;

    static void resetaOperacoes() {
        operacoes = 0;
    }

    static void imprimirCabecalho() {
        System.out.println("algoritmo;entrada;operacoes;tempo_ms;resultado");
    }

    static void imprimirLinha(String algoritmo, String entrada, long ops, double tempoMs, String resultado) {
        System.out.printf("%s;%s;%d;%.3f;%s%n", algoritmo, entrada, ops, tempoMs, resultado);
    }

    /**
     * Código 1: conta quantos valores ímpares existem nas posições pares do vetor.
     *
     * @param vetor vetor de entrada (não é modificado).
     * @return quantidade de ímpares nas posições 0, 2, 4, ...
     */
    static int codigo1(int[] vetor) {
        int resposta = 0;
        for (int i = 0; i < vetor.length; i += 2) {
            operacoes++; 
            resposta += vetor[i] % 2;
        }
        return resposta;
    }

    /**
     * Código 2: conta quantas vezes o laço interno executa, enquanto o limite externo é dividido por 2.
     *
     * @param vetor vetor usado apenas para obter o tamanho n.
     * @return total de vezes que o contador foi incrementado.
     */
    static int codigo2(int[] vetor) {
        int contador = 0;
        for (int k = (vetor.length - 1); k > 0; k /= 2) {
            operacoes++; 
            for (int i = 0; i <= k; i++) {
                contador++;
                operacoes++;
            }

        }
        return contador;
    }

    /**
     * Código 3: ordena o vetor em ordem crescente usando Selection Sort.
     *
     * @param vetor vetor a ser ordenado (é modificado).
     */
    static void codigo3(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
                operacoes++; 
                if (vetor[j] < vetor[menor]) {
                    menor = j;
                }
            }
            int temp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = temp;
            operacoes += 3; 
        }
    }

    /**
     * Código 4 (recursivo): calcula Fibonacci na forma recursiva.
     *
     * @param n posição da sequência (para n <= 2, retorna 1).
     * @return fib(n).
     */
    static int codigo4(int n) {
        operacoes++;
        if (n <= 2)
            return 1;
        else
            return codigo4(n - 1) + codigo4(n - 2);
    }

    /**
     * Gera um vetor pseudoaleatório de tamanho informado.
     *
     * @param tamanho tamanho do vetor.
     * @return vetor preenchido com valores aleatórios entre 1 e (tamanho/2).
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;
        
    }

    static void executarCodigo1ComTesteGrande() {
        for (int tamanho : tamanhosTesteGrande) {
            try {
                int[] vetor = gerarVetor(tamanho);
                resetaOperacoes();
                long inicio = System.nanoTime();
                int resposta = codigo1(vetor);
                long fim = System.nanoTime();
                imprimirLinha("codigo1", String.valueOf(tamanho), operacoes, (fim - inicio) * nanoToMilli, String.valueOf(resposta));
            } catch (OutOfMemoryError oom) {
                imprimirLinha("codigo1", String.valueOf(tamanho), -1, -1, "deu b.o");
            }
        }
    }

    static void executarCodigo2ComTesteGrande() {
        for (int tamanho : tamanhosTesteGrande) {
            try {
                int[] vetor = gerarVetor(tamanho);
                resetaOperacoes();
                long inicio = System.nanoTime();
                int contador = codigo2(vetor);
                long fim = System.nanoTime();
                imprimirLinha("codigo2", String.valueOf(tamanho), operacoes, (fim - inicio) * nanoToMilli, String.valueOf(contador));
            } catch (OutOfMemoryError oom) {
                imprimirLinha("codigo2", String.valueOf(tamanho), -1, -1, "deu b.o");
            }
        }
    }

    static void executarCodigo3ComTesteMedio() {
        for (int tamanho : tamanhosTesteMedio) {
            int[] vetor = gerarVetor(tamanho);
            resetaOperacoes();
            long inicio = System.nanoTime();
            codigo3(vetor);
            long fim = System.nanoTime();
            imprimirLinha("codigo3", String.valueOf(tamanho), operacoes, (fim - inicio) * nanoToMilli, "OK");
        }
    }

    static void executarCodigo4ComTestePequeno() {
        for (int n : tamanhosTestePequeno) {
            resetaOperacoes();
            long inicio = System.nanoTime();
            int resposta = codigo4(n);
            long fim = System.nanoTime();
            imprimirLinha("codigo4", String.valueOf(n), operacoes, (fim - inicio) * nanoToMilli, String.valueOf(resposta));
        }
    }

    public static void main(String[] args) {
        imprimirCabecalho();
        executarCodigo1ComTesteGrande();
        executarCodigo2ComTesteGrande();
        executarCodigo3ComTesteMedio();
        executarCodigo4ComTestePequeno();
    }
}

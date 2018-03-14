#include <conio.h>
#include <stdio.h>
#include <xmmintrin.h>
#include <immintrin.h>

int main(void) {
	char qw1[8] = { 1, 1, 1, 1, 1, 1, 1, 1 };
	char qw2[8] = { 2, 2, 2, 2, 2, 2, 2, 2 };

	int a = 1;
	int b = 2;
	float c[4] = { 1, 2, 3, 4 };
	float d[4] = { 5, 6, 7, 8 };
	double f[2] = { 16, 4 };

	char a128[16] = { 1, 18, 3, 19, 5, 21, 7, 23, 9, 25, 11, 27, 13, 29, 15, 31 };
	char b128[16] = { 17, 2, 19, 4, 21, 6, 23, 8, 25, 10, 27, 12, 29, 14, 31, 16 };

	float a256[16] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 25, 11, 27, 13, 29, 15, 31 };
	float b256[16] = { 5, 6, 7, 8, 9, 1, 2, 3, 4, 10, 27, 12, 29, 14, 31, 16 };

	const float floatAVX = 0.3;

	const float aAVX[8] = { 1, 2, 3, 4, 5, 6, 7, 8 };
	const float bAVX[8] = { 5, 6, 7, 8, 9, 10, 11, 12 };
	const float cAVX[8] = { 3, 4, 5, 6, 9, 10, 11, 12 };
	const float vecAVX[4] = { 2, 4, 2, 4 };
	float *vecOutAVX[4] = { 0, 0, 0, 0 };


	__m128 gg = _mm_shuffle_ps(*(__m128*)aAVX, *(__m128*)bAVX, 5);

	//__m256 tata = _mm256_and_ps(*(__m256*)aAVX, *(__m256*)bAVX);
	//__m256 tata2 = _mm256_andnot_ps(*(__m256*)aAVX, *(__m256*)bAVX);
	//__m256 TATA = _mm256_add_ps(*(__m256*)aAVX, *(__m256*)bAVX);

	__m128 ar = _mm_set1_ps(2.0);
	__m128 br = _mm_set1_ps(1.0);


	__m128 v = _mm_max_ps(ar/**(__m128*)aAVX*/, br/**(__m128*)bAVX*/);
	__m64 h = _m_pmaxub(*(__m64*)aAVX, *(__m64*)bAVX);
	

	//v = 



	/*_asm {
	movups xmm1, vecAVX
	vaddps xmm2, xmm0, xmm1
	//vpunpcklbw xmm0, xmm0, xmm1

	}*/

	//__m128 SSEa = _mm_load1_ps(&aAVX[0]);
	//__m128 SSEb = _mm_load1_ps(&bAVX[0]);
	//__m128 v	= _mm_load_ps(&vecAVX[0]);

	//__m128 v = _mm_add_ps(*(__m128*)aAVX, *(__m128*)bAVX);
	//_mm_store_ps(&vecAVX[0], v);
	//_mm_store_ps(vecOutAVX[0], v);
	/*
	_asm {
		//mmx

		movq	mm0, qw1
		movq	mm1, qw2

		// paddb mm0, mm1; сложение mm0 с mm1(= среднее арифметическое между mm0 и mm1)
		paddusb	mm0, mm1
		pcmpeqb	mm0, mm1
		movq	qw1, mm0
	}
	printf("%s\n", "Summing elements of vectors qw1 + qw2 :");

	for (int i = 0; i < 8; i++)
	{
		printf("%d ", qw1[i]);
	}

	printf("\n");

	_asm {
		//sse

		movups	xmm0, c
		movups	xmm1, d
		addps	xmm0, xmm1
		movups	c, xmm0
	}
	printf("%s\n", "Summing elements of vectors c + d :\n");
	for (int i = 0; i < 4; i++)
	{
		printf("%f ", c[i]);
	}

	_asm {
		//sse2
		movups xmm1, f
		sqrtpd xmm0, xmm1
		movups f, xmm0
	}

	printf("\n%s %f %s %f\n", "Square of ", f[0], "is", f[1]);

	_asm {
		//sse
		movups	xmm0, a128
		movups	xmm1, b128
		pminub	xmm0, xmm1
		movups	a128, xmm0
	}
	*/

	_asm {
		vmovups	ymm0, aAVX
		vmovups	ymm1, bAVX
		vaddps	ymm0, ymm1, ymm2
		vmovups	aAVX, ymm2
	}

	printf("\n%s\n", "Comparing elements :");
	for (int i = 0; i<16; i++)
	{
		printf("( %d , %d) ; ", a128[i], b128[i]);
	}

	printf("\n%s\n", "Minimum elements :");
	for (int i = 0; i<16; i++)
	{
		printf("%d ", a128[i]);
	}

	_getch();

	return 0;
}
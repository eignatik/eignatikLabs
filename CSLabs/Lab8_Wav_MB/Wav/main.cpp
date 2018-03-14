#include <stdio.h>
#include <stdlib.h>
#include <string>

typedef struct chunk {
	char ckID[4];
	unsigned int ckSize;
	char ckData[];
} chunk;

typedef struct fmt_data {
	unsigned short	AudioFormat; 
	unsigned short	NumOfChan;
	unsigned int	SamplesPerSec;
	unsigned int	bytesPerSec;
	unsigned short	blockAlign;
	unsigned short	bitsPerSample;
} fmt_data;

int main(int args_n, char **args)
{
	FILE *fp;
	long size;
	char *buffer;

	fp = fopen("sound.wav", "rb");
	if (!fp)
		return 1;

	fseek(fp, 0, SEEK_END);
	size = ftell(fp);
	fseek(fp, 0, SEEK_SET);

	buffer = (char*)malloc(size);

	if (!buffer)
	{
		fclose(fp);
		return 1;
	}

	fread(buffer, size, 1, fp);
	fclose(fp);

	chunk *RIFF = (chunk *)(buffer);

	long pointer = 4; // 4 для игнорирования символов "WAVE"

	chunk *fmt = (chunk *)(RIFF->ckData + pointer);
	fmt_data *fmt_dt = (fmt_data *)(fmt->ckData);

	switch (fmt_dt->AudioFormat)
	{
		case 1:
		{
			printf("Audio format: PCM\n");
			break;
		}
		case 6:
		{
			printf("Audio format: mulaw\n");
			break;
		}
		case 7:
		{
			printf("Audio format: alaw\n");
			break;
		}
		case 257:
		{
			printf("Audio format: IBM Mu-Law\n");
			break;
		}
		case 258:
		{
			printf("Audio format: IBM A-Law\n");
			break;
		}
		case 259:
		{
			printf("Audio format: ADPCM\n");
			break;
		}
		default: printf("Unknown audio format :(\n");
	}

	printf("Number of channels: %i\n", fmt_dt->NumOfChan);
	printf("Sampling Frequency in Hz: %i\n", fmt_dt->SamplesPerSec);
	printf("Bytes per second: %i\n", fmt_dt->bytesPerSec);

	switch (fmt_dt->blockAlign)
	{
		case 2:
		{
			printf("Block align: 16-bit mono\n");
			break;
		}
		case 4:
		{
			printf("Block align: 16-bit stereo\n");
			break;
		}
		default: printf("Unknown block align :(\n");
	}
	
	printf("Number of bits per sample: %i\n", fmt_dt->bitsPerSample);

	pointer += sizeof fmt_data + 8;
	chunk *LIST = (chunk *)(RIFF->ckData + pointer);

	/*while (LIST->ckID[0] != 'L' && LIST->ckID[1] != 'I' && LIST->ckID[1] != 'S' && LIST->ckID[1] != 'T')
	{
		pointer += LIST->ckSize + 8;
		LIST = (chunk *)(RIFF->ckData + pointer);

		if (pointer >= size)
		{
			pointer = size;
			// Здесь нужно создать LIST chunk
			break;
		}
	}*/


	char chunkListId[4] = { 'L', 'I', 'S', 'T' };
	unsigned int chunkListSize = 16;

	char chunkInfoId[4] = { 'I', 'N', 'F', 'O' };
	char chunkIARTId[4] = { 'I', 'A', 'R', 'T' };
	unsigned int chunkIARTSize = 4;
	char chunkIARTData[] = "Max";

	RIFF->ckSize += 24;

	fp = fopen("out.wav", "wb");

	if (!fp)
		return 1;

	fwrite(buffer, 1, size, fp);

	fwrite(chunkListId, 1, 4, fp);
	fwrite(&chunkListSize, sizeof(int), 1, fp);
	fwrite(chunkInfoId, 1, 4, fp);
	fwrite(chunkIARTId, 1, 4, fp);
	fwrite(&chunkIARTSize, sizeof(int), 1, fp);
	fwrite(chunkIARTData, 1, 4, fp);

	fclose(fp);
	free(buffer);

	getchar();

	return 0;
}
#include "stdafx.h"
#include <windows.h>
#include <iostream>
#include <string>
using namespace std;

void main()
{
	string sFileName;
	BITMAPFILEHEADER bmpFileHeader;
	BITMAPINFOHEADER bmpInfoHeader;
	int Width, Height;
	RGBQUAD Palette[256];
	RGBTRIPLE *inBuf;
	BYTE *outBuf;
	HANDLE hInputFile, hOutFile;
	DWORD RW;
	//cout << "Enter the full name, please: ";
	//cin >> sFileName;
	std::wstring stemp = std::wstring(sFileName.begin(), sFileName.end());
	LPCWSTR sw = stemp.c_str();

	//hInputFile = CreateFile(sw, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, 0, NULL);
	hInputFile = CreateFile(L"pict.bmp", GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, 0, NULL);
	if (hInputFile == INVALID_HANDLE_VALUE)
	{
		std::cerr << "Invalid Handle\n";
		std::system("PAUSE");
		return;
	}

	hOutFile = CreateFile(L"Result.bmp", GENERIC_WRITE, 0, NULL, CREATE_NEW, 0, NULL);
	if (hOutFile == INVALID_HANDLE_VALUE)
	{
		CloseHandle(hInputFile);
		return;
	}
	// Считываем инфу
	ReadFile(hInputFile, &bmpFileHeader, sizeof(bmpFileHeader), &RW, NULL);
	ReadFile(hInputFile, &bmpInfoHeader, sizeof(bmpInfoHeader), &RW, NULL);

	// Установим указатель на начало растра
	SetFilePointer(hInputFile, bmpFileHeader.bfOffBits, NULL, FILE_BEGIN);
	Width = bmpInfoHeader.biWidth;
	Height = bmpInfoHeader.biHeight;

	// Выделим память
	inBuf = new RGBTRIPLE[Width];
	outBuf = new BYTE[Width];

	// Заполним заголовки
	bmpFileHeader.bfOffBits = sizeof(bmpFileHeader) + sizeof(bmpInfoHeader) + 1024;
	bmpInfoHeader.biBitCount = 8;
	bmpFileHeader.bfSize = bmpFileHeader.bfOffBits + Width * Height + Height * (3 * Width % 4);

	// Запишем заголовки
	WriteFile(hOutFile, &bmpFileHeader, sizeof(bmpFileHeader), &RW, NULL);
	WriteFile(hOutFile, &bmpInfoHeader, sizeof(bmpInfoHeader), &RW, NULL);
	// Палитра черно-белая

	int i = 0;
	for (int r = 0; r < 256; r += 32)
		for (int g = 0; g < 256; g += 32)
			for (int b = 0; b <= 196; b += 64)
			{
				Palette[i].rgbBlue = r;
				Palette[i].rgbGreen = g;
				Palette[i].rgbRed = b;
				++i;
			}

	WriteFile(hOutFile, Palette, 256 * sizeof(RGBQUAD), &RW, NULL);

	// Начнем преобразовывать
	for (int i = 0; i < Height; i++)
	{
		ReadFile(hInputFile, inBuf, sizeof(RGBTRIPLE) * Width, &RW, NULL);
		for (int j = 0; j < Width; j++)
			//outBuf[j] = 0.3*inBuf[j].rgbtRed + 0.59*inBuf[j].rgbtGreen + 0.11*inBuf[j].rgbtBlue;
			outBuf[j] = (inBuf[j].rgbtRed / 32) << 5 | (inBuf[j].rgbtGreen / 32) << 2 | inBuf[j].rgbtBlue / 64;

		WriteFile(hOutFile, outBuf, sizeof(BYTE) * Width, &RW, NULL);

		// Пишем мусор для выравнивания
		WriteFile(hOutFile, Palette, (3 * Width) % 4, &RW, NULL);
		SetFilePointer(hInputFile, Width % 4, NULL, FILE_CURRENT);
	}
	delete[] inBuf;
	delete[] outBuf;
	CloseHandle(hInputFile);
	CloseHandle(hOutFile);
	cout << "Updating has come to the end successfully!";
	system("pause");
}

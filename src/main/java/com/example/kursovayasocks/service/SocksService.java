package com.example.kursovayasocks.service;


import com.example.kursovayasocks.dto.SocksDto;
import com.example.kursovayasocks.dto.request.SocksParamsRequest;
import com.example.kursovayasocks.exception.InvalidRequestParamsException;
import com.example.kursovayasocks.exception.NotEnoughItemsException;
import com.example.kursovayasocks.exception.SocksNotFoundException;

public interface SocksService {

    SocksDto incomeSocks(SocksDto socksDto);

    SocksDto outcomeSocks(SocksDto socksDto) throws SocksNotFoundException, NotEnoughItemsException;

    long getSocksCountByParams(SocksParamsRequest params) throws InvalidRequestParamsException;
}


package com.example.kursovayasocks.dao.repository;



import com.example.kursovayasocks.model.Color;
import com.example.kursovayasocks.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocksRepository extends JpaRepository<Socks, Integer> {
    Socks findSockByColorAndCottonPart(Color color, byte cottonPercent);

    List<Socks> findSocksByColor(Color color);

    //findAll()

    List<Socks> findSocksByCottonPartEquals(byte cottonPercent);

    List<Socks> findSocksByCottonPartGreaterThan(byte cottonPercent);

    List<Socks> findSocksByCottonPartLessThan(byte cottonPercent);

    List<Socks> findSocksByColorAndCottonPartEquals(Color color, byte cottonPercent);

    List<Socks> findSocksByColorAndCottonPartGreaterThan(Color color, byte cottonPercent);

    List<Socks> findSocksByColorAndCottonPartLessThan(Color color, byte cottonPercent);
}



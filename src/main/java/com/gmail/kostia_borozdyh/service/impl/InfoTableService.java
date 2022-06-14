package com.gmail.kostia_borozdyh.service.impl;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;
import com.gmail.kostia_borozdyh.service.IInfoTableService;
import com.gmail.kostia_borozdyh.util.Calculate;
import com.gmail.kostia_borozdyh.util.Table;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoTableService implements IInfoTableService {
    @Override
    public List<Integer> getPageNumberList(List<InfoTableDTO> infoTable) {
        return Calculate.getPaginationList(infoTable);
    }

    @Override
    public List<InfoTableDTO> getInfoTable(String cityFrom, String cityTo) {
        try {
            return Table.getInfoTable(cityFrom, cityTo);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InfoTableDTO> getShortInfoTable(List<InfoTableDTO> infoTable, List<Integer> list) {
        if (list == null) {
            return infoTable;
        } else {
            return Calculate.getFiveElements(infoTable, 1);
        }
    }

    @Override
    public InfoTableDTO getInfoTableById(List<InfoTableDTO> infoTable, Integer id) {
        InfoTableDTO tableDTO = null;
        for (InfoTableDTO table :
                infoTable) {
            if (table.getId() == id) tableDTO = table;
        }
        return tableDTO;
    }

}

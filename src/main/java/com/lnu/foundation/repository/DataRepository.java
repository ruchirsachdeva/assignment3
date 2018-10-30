package com.lnu.foundation.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.lnu.foundation.model.Data;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
public class DataRepository {

    public static  List<Data> loadData(String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            InputStream resourceAsStream = DataRepository.class.getResourceAsStream("/data/"+fileName+".csv");
            MappingIterator<Data> readValues =
                    mapper.reader(Data.class).with(bootstrapSchema).readValues(resourceAsStream);
            return readValues.readAll();
        } catch (Exception e) {
            System.out.println(e);
            return Collections.emptyList();
        }
    }
}

package com.imooc.sell.Utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 将时间从精确到毫秒修改为精确到秒
 * 也就是将Date转换为long
 * @author YJB
 * 2019-07-07
 */
public class Date2LongSerializer extends JsonSerializer<Date> {
    /**
     * 需要重写一个方法，就是serialize()
     */
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime()/1000);
    }
}

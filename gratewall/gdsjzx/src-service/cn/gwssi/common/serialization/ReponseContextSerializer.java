package cn.gwssi.common.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import cn.gwssi.common.model.SynReponseContext;

/**
 * ReponseContext序列化
 * @author xue
 * @version 1.0
 * @since 2016/5/1
 */
public class ReponseContextSerializer implements Serializer<SynReponseContext>{

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public byte[] serialize(String topic, SynReponseContext data) {
		try {
			byte[] bytes = null;
			if (data == null) {
				return null;
			}
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(data);
			//oo.writeUnshared(obj)
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
			return bytes;
		} catch (IOException e) {
			throw new SerializationException("Error when serializing byte[] to ReponseContext due to IO");
		}
		
	}

	@Override
	public void close() {
	}
}

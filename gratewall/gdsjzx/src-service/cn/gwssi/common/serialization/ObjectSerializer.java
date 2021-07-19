package cn.gwssi.common.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 * 对象反序列化
 * @author xue
 * @version 1.0
 * @since 2016/5/1
 */
public class ObjectSerializer implements Serializer<Object>{

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public byte[] serialize(String topic, Object data) {
		try {
			byte[] bytes = null;
			if (data == null) {
				return null;
			}
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(data);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
			return bytes;
		} catch (IOException e) {
			throw new SerializationException("Error when deserializing byte[] to Object due to IO");
		}
	}

	@Override
	public void close() {
	}
}

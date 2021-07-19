package cn.gwssi.common.serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import cn.gwssi.common.model.SynReponseContext;

/**
 * ReponseContext反序列化
 * @author xue
 * @version 1.0
 * @since 2016/5/1
 */
public class ReponseContextDeserializer implements Deserializer<SynReponseContext>{
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}
	
	@Override
	public SynReponseContext deserialize(String topic, byte[] data) {
		try {
			if (data == null) {
			    return null;
			  }
			SynReponseContext ob = null;
			 ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			 ob = (SynReponseContext) ois.readObject();
			 ois.close();
			 return ob;
		} catch (ClassNotFoundException e) {
			throw new SerializationException("Error when deserializing byte[] to SynReponseContext due to Can't find this class");
		} catch (IOException e) {
			throw new SerializationException("Error when deserializing byte[] to SynReponseContext due to IO");
		}
	}
	
	@Override
	public void close() {
	}
	
}

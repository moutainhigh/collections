package cn.gwssi.common.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.kafka.common.errors.SerializationException;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class ObjectEncoder implements Encoder<Object>{
	private final String encoding;
	@Override
	public byte[] toBytes(Object obj) {
		try {
			byte[] bytes = null;
			if (obj == null) {
				return null;
			}
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
			return bytes;
		} catch (IOException e) {
			throw new SerializationException("Error when ObjectEncoder byte[] to Object due to IO");
		}
	}
	public ObjectEncoder(VerifiableProperties props){
	    this.encoding = ((props == null) ? "UTF8" : props.getString("serializer.encoding", "UTF8"));
	  }
}

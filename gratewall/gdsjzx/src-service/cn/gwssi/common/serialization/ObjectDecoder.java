package cn.gwssi.common.serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.kafka.common.errors.SerializationException;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

public class ObjectDecoder implements Decoder<Object>{
	private final String encoding;
	@Override
	public Object fromBytes(byte[] data) {
		try {
			if (data == null) {
				return null;
			}
			Object ob = null;
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			ob = ois.readObject();
			ois.close();
			return ob;
		} catch (ClassNotFoundException e) {
			throw new SerializationException("Error when deserializing byte[] to Object due to Can't find this class");
		} catch (IOException e) {
			throw new SerializationException("Error when deserializing byte[] to Object due to IO");
		}
	}
	
	public ObjectDecoder(VerifiableProperties props)
	  {
	    this.encoding = ((props == null) ? "UTF8" : props.getString("serializer.encoding", "UTF8"));
	}

}

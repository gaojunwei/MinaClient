package common;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MyCodecFactory extends DemuxingProtocolCodecFactory {

	private MessageDecoder decoder;
	private MessageEncoder<AbsMessage> encoder;

	public MyCodecFactory(MessageDecoder decoder,
			MessageEncoder<AbsMessage> encoder) {
		this.decoder = decoder;
		this.encoder = encoder;
		addMessageDecoder(this.decoder);
		addMessageEncoder(AbsMessage.class, this.encoder);
	}
}
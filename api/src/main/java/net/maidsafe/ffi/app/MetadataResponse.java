package net.maidsafe.ffi.app;

/// User metadata for mutable data
public class MetadataResponse {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String val) {
		name = val;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(final String val) {
		description = val;
	}

	private byte[] xorName;

	public byte[] getXorName() {
		return xorName;
	}

	public void setXorName(final byte[] val) {
		xorName = val;
	}

	private long typeTag;

	public long getTypeTag() {
		return typeTag;
	}

	public void setTypeTag(final long val) {
		typeTag = val;
	}

}


package com.sunmi.trans;

import android.os.Parcel;
import android.os.Parcelable;

public class TransBean implements Parcelable {

	private byte type = 0;
	private String text = "";
	private byte[] data = null;
	private int datalength = 0;
	
	public TransBean(){
		type = 0;
		data = null;
		text = "";
		datalength = 0;		
	};
	
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		if(data != null){
			datalength = data.length;
			this.data = new byte[datalength];
			System.arraycopy(data, 0, this.data, 0, datalength);
		}
	}

	public TransBean(Parcel source){
		//TODO：注意，读取包中的数据和打包是存入的数据格式顺序，是一致的，否则不能正确读取数据

		this.type = source.readByte();
		this.datalength = source.readInt();
		this.text = source.readString();
		if(datalength > 0){
			this.data = new byte[datalength];
			source.readByteArray(data);
		}
	}
	
	public TransBean(byte type, String text, byte[] data){
		this.type = type;
		this.text = text;
		if(data != null){
			this.datalength = data.length;
			this.data = new byte[datalength];
			System.arraycopy(data, 0, this.data, 0, datalength);
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	//将当前对象的属性打包，写到包对象中
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(type);
		dest.writeInt(datalength);
		dest.writeString(text);
		if(data != null){
			dest.writeByteArray(data);
		}
	}

	//这种写法是ADIL的规范，必须这么写
	// 添加一个静态成员，名为CREATOR，该对象实现了Parcelable.Creator接口
	public static Creator<TransBean> CREATOR = new Creator<TransBean>(){

		//解包：读取包中的数据并封装成对象
		@Override
		public TransBean createFromParcel(Parcel source) {
			return new TransBean(source);
		}

		//返回一个指定大小的对象容器
		@Override
		public TransBean[] newArray(int size) {
			return new TransBean[size];
		}		
	};

}

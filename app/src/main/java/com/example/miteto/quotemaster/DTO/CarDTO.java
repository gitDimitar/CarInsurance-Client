package com.example.miteto.quotemaster.DTO;

import android.os.Parcel;
import android.os.Parcelable;

/*
    Created by  Dimitar Papazikov,
                Jake Carville,
                Chris Collins.
 */
public class CarDTO implements Parcelable
{
    private String id;
    private String make;
    private String model;
    private double engineSize;

    public CarDTO()
    {
        super();
    }

    public CarDTO(String id, String make, String model, double engineSize)
    {
        this.id = id;
        this.make = make;
        this.model = model;
        this.engineSize = engineSize;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public double getEngineSize()
    {
        return engineSize;
    }

    public void setEngineSize(double size)
    {
        this.engineSize = size;
    }

    @Override
    public String toString()
    {
        return "CarDTO{" +
                "id='" + id + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", engineSize=" + engineSize +
                '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(make);
        dest.writeString(model);
        dest.writeDouble(engineSize);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CarDTO createFromParcel(Parcel in) {
            return new CarDTO(in);
        }

        public CarDTO[] newArray(int size) {
            return new CarDTO[size];
        }
    };

    public CarDTO(Parcel in)
    {
        this.id = in.readString();
        this.make = in.readString();
        this.model = in.readString();
        this.engineSize = in.readDouble();
    }


}

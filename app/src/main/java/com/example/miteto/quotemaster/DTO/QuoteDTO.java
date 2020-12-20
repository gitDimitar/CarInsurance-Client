package com.example.miteto.quotemaster.DTO;


import android.os.Parcel;
import android.os.Parcelable;

/*
    Created by  Dimitar Papazikov,
                Jake Carville,
                Chris Collins.
 */
public class QuoteDTO implements Parcelable
{
    private CarDTO car;
    private int id;
    private String fName;
    private String lName;
    private int age;
    private int noClaims;
    private String licence;
    private int penaltyPoints;
    private double tpPrice;
    private double tpMonthlyPrice;
    private double fullPrice;
    private double fullMonthlyPrice;

    public QuoteDTO()
    {
        super();
    }

    public QuoteDTO(CarDTO car, int id , String fName, String lName, int age, int noClaims, String licence, int penaltyPoints, double tpPrice, double tpMonthlyPrice, double fullPrice, double fullMonthlyPrice)
    {
        this.car = car;
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.noClaims = noClaims;
        this.licence = licence;
        this.penaltyPoints = penaltyPoints;
        this.tpPrice = tpPrice;
        this.tpMonthlyPrice = tpMonthlyPrice;
        this.fullPrice = fullPrice;
        this.fullMonthlyPrice = fullMonthlyPrice;
    }

    public QuoteDTO(CarDTO car, String fName, String lName, int age, int noClaims, String licence, int penaltyPoints)
    {
        this.car = car;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.noClaims = noClaims;
        this.licence = licence;
        this.penaltyPoints = penaltyPoints;

    }

    public String getfName()
    {
        return fName;
    }

    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getlName()
    {
        return lName;
    }

    public void setlName(String lName)
    {
        this.lName = lName;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getNoClaims()
    {
        return noClaims;
    }

    public void setNoClaims(int noClaims)
    {
        this.noClaims = noClaims;
    }

    public String getLicence()
    {
        return licence;
    }

    public void setLicence(String licence)
    {
        this.licence = licence;
    }

    public int getPenaltyPoints()
    {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints)
    {
        this.penaltyPoints = penaltyPoints;
    }

    public CarDTO getCar()
    {
        return car;
    }

    public void setCar(CarDTO car)
    {
        this.car = car;
    }

    public double getTpPrice()
    {
        return tpPrice;
    }

    public void setTpPrice(double tpPrice)
    {
        this.tpPrice = tpPrice;
    }

    public double getTpMonthlyPrice()
    {
        return tpMonthlyPrice;
    }

    public void setTpMonthlyPrice(double tpMonthlyPrice)
    {
        this.tpMonthlyPrice = tpMonthlyPrice;
    }

    public double getFullPrice()
    {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice)
    {
        this.fullPrice = fullPrice;
    }

    public double getFullMonthlyPrice()
    {
        return fullMonthlyPrice;
    }

    public void setFullMonthlyPrice(double fullMonthlyPrice)
    {
        this.fullMonthlyPrice = fullMonthlyPrice;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "QuoteDTO{" +
                "car=" + car +
                ", id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", age=" + age +
                ", noClaims=" + noClaims +
                ", licence='" + licence + '\'' +
                ", penaltyPoints=" + penaltyPoints +
                ", tpPrice=" + tpPrice +
                ", tpMonthlyPrice=" + tpMonthlyPrice +
                ", fullPrice=" + fullPrice +
                ", fullMonthlyPrice=" + fullMonthlyPrice +
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
        dest.writeParcelable(car, flags);
        dest.writeInt(id);
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeInt(age);
        dest.writeInt(noClaims);
        dest.writeString(licence);
        dest.writeInt(penaltyPoints);
        dest.writeDouble(tpPrice);
        dest.writeDouble(tpMonthlyPrice);
        dest.writeDouble(fullPrice);
        dest.writeDouble(fullMonthlyPrice);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<QuoteDTO> CREATOR = new Parcelable.Creator<QuoteDTO>() {
        @Override
        public QuoteDTO createFromParcel(Parcel in) {
            return new QuoteDTO(in);
        }

        @Override
        public QuoteDTO[] newArray(int size) {
            return new QuoteDTO[size];
        }
    };

    protected QuoteDTO(Parcel in)
    {
        this.car =(CarDTO) in.readParcelable(CarDTO.class.getClassLoader());
        this.id = in.readInt();
        this.fName = in.readString();
        this.lName = in.readString();
        this.age = in.readInt();
        this.noClaims = in.readInt();
        this.licence = in.readString();
        this.penaltyPoints = in.readInt();
        this.tpPrice = in.readDouble();
        this.tpMonthlyPrice = in.readDouble();
        this.fullPrice = in.readDouble();
        this.fullMonthlyPrice = in.readDouble();

    }


}

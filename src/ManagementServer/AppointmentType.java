package ManagementServer;


/**
* ManagementServer/AppointmentType.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ManagementServer.idl
* Monday, March 4, 2024 6:34:34 o'clock PM EST
*/

public class AppointmentType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static ManagementServer.AppointmentType[] __array = new ManagementServer.AppointmentType [__size];

  public static final int _Physician = 0;
  public static final ManagementServer.AppointmentType Physician = new ManagementServer.AppointmentType(_Physician);
  public static final int _Surgeon = 1;
  public static final ManagementServer.AppointmentType Surgeon = new ManagementServer.AppointmentType(_Surgeon);
  public static final int _Dental = 2;
  public static final ManagementServer.AppointmentType Dental = new ManagementServer.AppointmentType(_Dental);

  public int value ()
  {
    return __value;
  }

  public static ManagementServer.AppointmentType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected AppointmentType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class AppointmentType

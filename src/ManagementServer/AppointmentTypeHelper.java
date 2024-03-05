package ManagementServer;


/**
* ManagementServer/AppointmentTypeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ManagementServer.idl
* Monday, March 4, 2024 10:14:57 o'clock PM EST
*/

abstract public class AppointmentTypeHelper
{
  private static String  _id = "IDL:ManagementServer/AppointmentType:1.0";

  public static void insert (org.omg.CORBA.Any a, ManagementServer.AppointmentType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ManagementServer.AppointmentType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (ManagementServer.AppointmentTypeHelper.id (), "AppointmentType", new String[] { "Physician", "Surgeon", "Dental"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static ManagementServer.AppointmentType read (org.omg.CORBA.portable.InputStream istream)
  {
    return ManagementServer.AppointmentType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ManagementServer.AppointmentType value)
  {
    ostream.write_long (value.value ());
  }

}

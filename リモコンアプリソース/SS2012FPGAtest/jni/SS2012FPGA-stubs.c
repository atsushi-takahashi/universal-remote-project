/*
 * This file was generated by orbit-idl-2 - DO NOT EDIT!
 */

#include <string.h>
#define ORBIT2_STUBS_API
#include "SS2012FPGA.h"

void
jp_ac_utsunomiya_is_SS2012FPGA_setMotorTorque
   (CORBA_jp_ac_utsunomiya_is_SS2012FPGA _obj, CORBA_long motorID,
    CORBA_long maxTorque, CORBA_long torque, CORBA_Environment * ev)
{
   void *_args[3];

   _args[0] = (void *) &motorID;
   _args[1] = (void *) &maxTorque;
   _args[2] = (void *) &torque;
   invokeMethod(_obj, &jp_ac_utsunomiya_is_SS2012FPGA__imp.methods[0], NULL,
		_args, ev);
}

void
jp_ac_utsunomiya_is_SS2012FPGA_playAlarmSound
   (CORBA_jp_ac_utsunomiya_is_SS2012FPGA _obj, CORBA_Environment * ev)
{
   invokeMethod(_obj, &jp_ac_utsunomiya_is_SS2012FPGA__imp.methods[1], NULL,
		NULL, ev);
}

CORBA_long
jp_ac_utsunomiya_is_SS2012FPGA_getAlarmSwitchState
   (CORBA_jp_ac_utsunomiya_is_SS2012FPGA _obj, CORBA_Environment * ev)
{
   CORBA_long _ORBIT_retval;

   invokeMethod(_obj, &jp_ac_utsunomiya_is_SS2012FPGA__imp.methods[2],
		(void **) &_ORBIT_retval, NULL, ev);
   return _ORBIT_retval;
}

void
jp_ac_utsunomiya_is_SS2012FPGA_sendIrDAdata
   (CORBA_jp_ac_utsunomiya_is_SS2012FPGA _obj, CORBA_long dataHigh,
    CORBA_short dataLow, CORBA_Environment * ev)
{
   void *_args[2];

   _args[0] = (void *) &dataHigh;
   _args[1] = (void *) &dataLow;
   invokeMethod(_obj, &jp_ac_utsunomiya_is_SS2012FPGA__imp.methods[3], NULL,
		_args, ev);
}

void
jp_ac_utsunomiya_is_SS2012FPGA_receiveIrDAdata
   (CORBA_jp_ac_utsunomiya_is_SS2012FPGA _obj, CORBA_long dataHigh,
    CORBA_short dataLow, CORBA_Environment * ev)
{
   void *_args[2];

   _args[0] = &dataHigh;
   _args[1] = &dataLow;
   invokeMethod(_obj, &jp_ac_utsunomiya_is_SS2012FPGA__imp.methods[4], NULL,
		_args, ev);
}

#if __cplusplus
__attribute__ ((weak)) jp::ac::utsunomiya::is::SS2012FPGA_ptr jp::ac::
   utsunomiya::is::SS2012FPGA::_narrow(CORBA::Object_ptr obj)
{
   return CORBA_Narrow < jp::ac::utsunomiya::is::SS2012FPGA >::narrow(obj);
}

void
jp::ac::utsunomiya::is::SS2012FPGA::setMotorTorque(CORBA::Long motorID,
						   CORBA::Long maxTorque,
						   CORBA::Long torque)
{
   jp_ac_utsunomiya_is_SS2012FPGA_setMotorTorque(this->_impl,
						 CORBA::
						 Long_CppInArg(motorID),
						 CORBA::
						 Long_CppInArg(maxTorque),
						 CORBA::Long_CppInArg(torque),
						 &this->ev);
   return;
}

void
jp::ac::utsunomiya::is::SS2012FPGA::playAlarmSound()
{
   jp_ac_utsunomiya_is_SS2012FPGA_playAlarmSound(this->_impl, &this->ev);
   return;
}

CORBA::Long jp::ac::utsunomiya::is::SS2012FPGA::getAlarmSwitchState()
{
   CORBA_long
      tmp =
      jp_ac_utsunomiya_is_SS2012FPGA_getAlarmSwitchState(this->_impl,
							 &this->ev);
   return CORBA::Long_helper::_retnew(tmp);
}

void
jp::ac::utsunomiya::is::SS2012FPGA::sendIrDAdata(CORBA::Long dataHigh,
						 CORBA::Short dataLow)
{
   jp_ac_utsunomiya_is_SS2012FPGA_sendIrDAdata(this->_impl,
					       CORBA::Long_CppInArg(dataHigh),
					       CORBA::Short_CppInArg(dataLow),
					       &this->ev);
   return;
}

void
jp::ac::utsunomiya::is::SS2012FPGA::receiveIrDAdata(CORBA::Long_out dataHigh,
						    CORBA::Short_out dataLow)
{
   jp_ac_utsunomiya_is_SS2012FPGA_receiveIrDAdata(this->_impl,
						  CORBA::
						  Long_CppOutArg(dataHigh),
						  CORBA::
						  Short_CppOutArg(dataLow),
						  &this->ev);
   return;
}

#endif /// __cplusplus

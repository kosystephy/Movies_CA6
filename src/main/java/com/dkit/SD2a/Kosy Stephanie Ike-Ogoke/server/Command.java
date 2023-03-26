package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.Packet;

public interface Command
{
    public Packet createResponse(Packet incomingPacket);
}

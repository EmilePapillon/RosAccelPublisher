package com.example.rosaccelpublisher;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class MainActivity extends RosActivity
{
    protected MainActivity(String notificationTicker, String notificationTitle) {
        super(notificationTicker, notificationTitle);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor)
    {
        AccelerationTalker aTalker = new AccelerationTalker(this);
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(
                InetAddressFactory.newNonLoopback().getHostAddress() );
        nodeConfiguration.setMasterUri(getMasterUri()) ;
        nodeMainExecutor.execute(aTalker, nodeConfiguration);

    }
}

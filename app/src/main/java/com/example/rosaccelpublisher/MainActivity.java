package com.example.rosaccelpublisher;

import org.apache.commons.lang.NullArgumentException;
import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.util.EmptyStackException;

public class MainActivity extends RosActivity
{
    private AccelerationTalker aTalker;

    public MainActivity()
    {
        super("Pubsub Tutorial", "Pubsub Tutorial");
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor)
    {
        aTalker = new AccelerationTalker(this);
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(
                InetAddressFactory.newNonLoopback().getHostAddress() );
        nodeConfiguration.setMasterUri(getMasterUri()) ;
        nodeMainExecutor.execute(aTalker, nodeConfiguration);

    }
}

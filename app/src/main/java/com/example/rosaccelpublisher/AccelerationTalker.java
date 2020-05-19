package com.example.rosaccelpublisher;
//import android.annotation.SuppressLint;
import android.content.Context;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import std_msgs.String;

public class AccelerationTalker implements NodeMain {

    private AccelerationListener aListener;

    public AccelerationTalker(Context c)
    {
        aListener = new AccelerationListener(c);
    }

    @Override
    public GraphName getDefaultNodeName()
    {
        return GraphName.of("AccelerationTalker/aTalker");
    }

    @Override
    public void onStart(final ConnectedNode node)
    {
        final Publisher<std_msgs.String> publisher=
                node.newPublisher("aChatter", "std_msgs/String");

        final CancellableLoop aLoop = new CancellableLoop()
        {
            @Override
            protected void loop() throws InterruptedException
            {
                std_msgs.String str = publisher.newMessage();

                float[] aValue =aListener.getSensorValue();
                str.setData(java.lang.String.format("%f, %f, %f", aValue[0],aValue[1],aValue[2]));
                publisher.publish(str);
                Thread.sleep(500);
            }
        };
        node.executeCancellableLoop(aLoop);
        aListener.onResume();
    }

    @Override
    public void onError(Node node, Throwable throwable)
    {
        //
    }

    @Override
    public void onShutdown(Node node)
    {
        aListener.onPause();
    }

    @Override
    public void onShutdownComplete(Node node)
    {
        //
    }
}

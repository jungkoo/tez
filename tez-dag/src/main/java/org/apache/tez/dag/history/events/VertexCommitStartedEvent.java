/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tez.dag.history.events;

import org.apache.tez.dag.history.HistoryEvent;
import org.apache.tez.dag.history.HistoryEventType;
import org.apache.tez.dag.records.TezVertexID;
import org.apache.tez.dag.recovery.records.RecoveryProtos.VertexCommitStartedProto;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VertexCommitStartedEvent implements HistoryEvent {

  private TezVertexID vertexID;

  public VertexCommitStartedEvent() {
  }

  public VertexCommitStartedEvent(TezVertexID vertexId) {
    this.vertexID = vertexId;
  }

  @Override
  public HistoryEventType getEventType() {
    return HistoryEventType.VERTEX_COMMIT_STARTED;
  }

  @Override
  public JSONObject convertToATSJSON() throws JSONException {
    // TODO
    return null;
  }

  @Override
  public boolean isRecoveryEvent() {
    return true;
  }

  @Override
  public boolean isHistoryEvent() {
    return false;
  }

  public VertexCommitStartedProto toProto() {
    return VertexCommitStartedProto.newBuilder()
        .setVertexId(vertexID.toString())
        .build();
  }

  public void fromProto(VertexCommitStartedProto proto) {
    this.vertexID = TezVertexID.fromString(proto.getVertexId());
  }

  @Override
  public void toProtoStream(OutputStream outputStream) throws IOException {
    toProto().writeDelimitedTo(outputStream);
  }

  @Override
  public void fromProtoStream(InputStream inputStream) throws IOException {
    VertexCommitStartedProto proto = VertexCommitStartedProto.parseDelimitedFrom(inputStream);
    fromProto(proto);
  }

  @Override
  public String toString() {
    return "vertexId=" + vertexID;
  }

  public TezVertexID getVertexID() {
    return this.vertexID;
  }

}

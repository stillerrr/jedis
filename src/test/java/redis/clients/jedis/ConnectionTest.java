package redis.clients.jedis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.exceptions.JedisConnectionException;

public class ConnectionTest {

  private Connection client;

  @After
  public void tearDown() throws Exception {
    if (client != null) {
      client.close();
    }
  }

  @Test(expected = JedisConnectionException.class)
  public void checkUnknownHost() {
    client = new Connection("someunknownhost", Protocol.DEFAULT_PORT);
    client.connect();
  }

  @Test(expected = JedisConnectionException.class)
  public void checkWrongPort() {
    client = new Connection(Protocol.DEFAULT_HOST, 55665);
    client.connect();
  }

  @Test
  public void connectIfNotConnectedWhenSettingTimeoutInfinite() {
    client = new Connection("localhost", 6379);
    client.setTimeoutInfinite();
  }

  @Test
  public void checkCloseable() {
    client = new Connection("localhost", 6379);
    client.connect();
    client.close();
  }

  @Test
  public void checkIdentityString() {
    client = new Connection("localhost", 6379);
    Assert.assertFalse(client.toIdentityString().contains("6379"));
    client.connect();
    Assert.assertTrue(client.toIdentityString().contains("6379"));
    client.close();
    Assert.assertTrue(client.toIdentityString().contains("6379"));
  }
}

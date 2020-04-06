package redisclient;

import static redisclient.TracingHelper.nullable;

import io.opentelemetry.context.Scope;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.TracingContextUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.Client;
import redis.clients.jedis.ClusterReset;
import redis.clients.jedis.DebugParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.JedisPoolAbstract;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.Module;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.StreamPendingEntry;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.params.ClientKillParams;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.params.MigrateParams;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.params.ZAddParams;
import redis.clients.jedis.params.ZIncrByParams;
import redis.clients.jedis.util.Slowlog;

public final class TracingJedisWrapper extends Jedis {
  public TracingJedisWrapper(final String host) {
    super(host);
  }

  @Override
  public Tuple zpopmax(String key) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmax");
    span.setAttribute("key", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmax(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zpopmax(String key, int count) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmax");
    span.setAttribute("key", key);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmax(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Tuple zpopmin(String key) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmin");
    span.setAttribute("key", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmin(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zpopmin(String key, int count) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmin");
    span.setAttribute("key", key);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmin(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String memoryDoctor() {
    Span span = TracingHelper.buildSpan("Redis.MemoryDoctor");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.memoryDoctor();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public StreamEntryID xadd(String key, StreamEntryID id, Map<String, String> hash) {
    Span span = TracingHelper.buildSpan("Redis.Xadd");
    span.setAttribute("key", key);
    span.setAttribute("id", nullable(id));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xadd(key, id, hash);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public StreamEntryID xadd(
      String key,
      StreamEntryID id,
      Map<String, String> hash,
      long maxLen,
      boolean approximateLength) {
    Span span = TracingHelper.buildSpan("Redis.Xadd");
    span.setAttribute("key", key);
    span.setAttribute("id", nullable(id));
    span.setAttribute("maxLen", maxLen);
    span.setAttribute("approximateLength", approximateLength);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xadd(key, id, hash, maxLen, approximateLength);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long xlen(String key) {
    Span span = TracingHelper.buildSpan("Redis.Xlen");
    span.setAttribute("key", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xlen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<StreamEntry> xrange(String key, StreamEntryID start, StreamEntryID end, int count) {
    Span span = TracingHelper.buildSpan("Redis.Xrange");
    span.setAttribute("key", key);
    span.setAttribute("start", nullable(start));
    span.setAttribute("end", nullable(end));
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xrange(key, start, end, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<StreamEntry> xrevrange(
      String key, StreamEntryID end, StreamEntryID start, int count) {
    Span span = TracingHelper.buildSpan("Redis.Xrevrange");
    span.setAttribute("key", key);
    span.setAttribute("start", nullable(start));
    span.setAttribute("end", nullable(end));
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xrevrange(key, start, end, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Entry<String, List<StreamEntry>>> xread(
      int count, long block, Entry<String, StreamEntryID>... streams) {
    Span span = TracingHelper.buildSpan("Redis.Xread");
    span.setAttribute("count", count);
    span.setAttribute("block", block);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xread(count, block, streams);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long xack(String key, String group, StreamEntryID... ids) {
    Span span = TracingHelper.buildSpan("Redis.Xack");
    span.setAttribute("key", key);
    span.setAttribute("group", group);
    span.setAttribute("ids", Arrays.toString(ids));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xack(key, group, ids);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String xgroupCreate(String key, String groupname, StreamEntryID id, boolean makeStream) {
    Span span = TracingHelper.buildSpan("Redis.XgroupCreate");
    span.setAttribute("key", key);
    span.setAttribute("groupname", groupname);
    span.setAttribute("id", nullable(id));
    span.setAttribute("makeStream", makeStream);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupCreate(key, groupname, id, makeStream);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String xgroupSetID(String key, String groupname, StreamEntryID id) {
    Span span = TracingHelper.buildSpan("Redis.XgroupSetID");
    span.setAttribute("key", key);
    span.setAttribute("groupname", groupname);
    span.setAttribute("id", nullable(id));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupSetID(key, groupname, id);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long xgroupDestroy(String key, String groupname) {
    Span span = TracingHelper.buildSpan("Redis.XgroupDestroy");
    span.setAttribute("key", key);
    span.setAttribute("groupname", groupname);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupDestroy(key, groupname);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String xgroupDelConsumer(String key, String groupname, String consumerName) {
    Span span = TracingHelper.buildSpan("Redis.XgroupDelConsumer");
    span.setAttribute("key", key);
    span.setAttribute("groupname", groupname);
    span.setAttribute("consumerName", consumerName);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupDelConsumer(key, groupname, consumerName);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long xdel(String key, StreamEntryID... ids) {
    Span span = TracingHelper.buildSpan("Redis.Xdel");
    span.setAttribute("key", key);
    span.setAttribute("ids", Arrays.toString(ids));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xdel(key, ids);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long xtrim(String key, long maxLen, boolean approximateLength) {
    Span span = TracingHelper.buildSpan("Redis.Xtrim");
    span.setAttribute("key", key);
    span.setAttribute("maxLen", maxLen);
    span.setAttribute("approximateLength", approximateLength);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xtrim(key, maxLen, approximateLength);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public final List<Entry<String, List<StreamEntry>>> xreadGroup(
      String groupname,
      String consumer,
      int count,
      long block,
      boolean noAck,
      Entry<String, StreamEntryID>... streams) {
    Span span = TracingHelper.buildSpan("Redis.XreadGroup");
    span.setAttribute("groupname", groupname);
    span.setAttribute("consumer", consumer);
    span.setAttribute("count", count);
    span.setAttribute("block", block);
    span.setAttribute("noAck", noAck);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xreadGroup(groupname, consumer, count, block, noAck, streams);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<StreamPendingEntry> xpending(
      String key,
      String groupname,
      StreamEntryID start,
      StreamEntryID end,
      int count,
      String consumername) {
    Span span = TracingHelper.buildSpan("Redis.Xpending");
    span.setAttribute("key", key);
    span.setAttribute("groupname", groupname);
    span.setAttribute("start", nullable(start));
    span.setAttribute("end", nullable(end));
    span.setAttribute("count", count);
    span.setAttribute("consumername", consumername);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xpending(key, groupname, start, end, count, consumername);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<StreamEntry> xclaim(
      String key,
      String group,
      String consumername,
      long minIdleTime,
      long newIdleTime,
      int retries,
      boolean force,
      StreamEntryID... ids) {
    Span span = TracingHelper.buildSpan("Redis.Xclaim");
    span.setAttribute("key", key);
    span.setAttribute("group", group);
    span.setAttribute("consumername", consumername);
    span.setAttribute("minIdleTime", minIdleTime);
    span.setAttribute("retries", retries);
    span.setAttribute("force", force);
    span.setAttribute("ids", Arrays.toString(ids));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xclaim(key, group, consumername, minIdleTime, newIdleTime, retries, force, ids);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object sendCommand(ProtocolCommand cmd, String... args) {
    Span span = TracingHelper.buildSpan("Redis.SendCommand");
    span.setAttribute("cmd", nullable(cmd));
    span.setAttribute("args", Arrays.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sendCommand(cmd, args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Tuple zpopmax(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmax");
    span.setAttribute("key", Arrays.toString(key));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmax(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zpopmax(byte[] key, int count) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmax");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmax(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Tuple zpopmin(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmin");
    span.setAttribute("key", Arrays.toString(key));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmin(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zpopmin(byte[] key, int count) {
    Span span = TracingHelper.buildSpan("Redis.Zpopmin");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zpopmin(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] memoryDoctorBinary() {
    Span span = TracingHelper.buildSpan("Redis.MemoryDoctorBinary");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.memoryDoctorBinary();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> xread(int count, long block, Map<byte[], byte[]> streams) {
    Span span = TracingHelper.buildSpan("Redis.Xread");
    span.setAttribute("count", count);
    span.setAttribute("block", block);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xread(count, block, streams);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> xreadGroup(
      byte[] groupname,
      byte[] consumer,
      int count,
      long block,
      boolean noAck,
      Map<byte[], byte[]> streams) {
    Span span = TracingHelper.buildSpan("Redis.XreadGroup");
    span.setAttribute("groupname", Arrays.toString(groupname));
    span.setAttribute("consumer", Arrays.toString(consumer));
    span.setAttribute("count", count);
    span.setAttribute("block", block);
    span.setAttribute("noAck", noAck);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xreadGroup(groupname, consumer, count, block, noAck, streams);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] xadd(
      byte[] key, byte[] id, Map<byte[], byte[]> hash, long maxLen, boolean approximateLength) {
    Span span = TracingHelper.buildSpan("Redis.Xadd");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("id", Arrays.toString(id));
    span.setAttribute("maxLen", maxLen);
    span.setAttribute("approximateLength", approximateLength);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xadd(key, id, hash, maxLen, approximateLength);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long xlen(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Xlen");
    span.setAttribute("key", Arrays.toString(key));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xlen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> xrange(byte[] key, byte[] start, byte[] end, long count) {
    Span span = TracingHelper.buildSpan("Redis.Xrange");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("start", Arrays.toString(start));
    span.setAttribute("end", Arrays.toString(end));
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xrange(key, start, end, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> xrevrange(byte[] key, byte[] end, byte[] start, int count) {
    Span span = TracingHelper.buildSpan("Redis.Xrevrange");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("end", Arrays.toString(end));
    span.setAttribute("start", Arrays.toString(start));
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xrevrange(key, end, start, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long xack(byte[] key, byte[] group, byte[]... ids) {
    Span span = TracingHelper.buildSpan("Redis.Xack");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("group", Arrays.toString(group));
    span.setAttribute("ids", TracingHelper.toString(ids));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xack(key, group, ids);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String xgroupCreate(byte[] key, byte[] consumer, byte[] id, boolean makeStream) {
    Span span = TracingHelper.buildSpan("Redis.XgroupCreate");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("consumer", Arrays.toString(consumer));
    span.setAttribute("id", Arrays.toString(id));
    span.setAttribute("makeStream", makeStream);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupCreate(key, consumer, id, makeStream);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String xgroupSetID(byte[] key, byte[] consumer, byte[] id) {
    Span span = TracingHelper.buildSpan("Redis.XgroupSetID");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("consumer", Arrays.toString(consumer));
    span.setAttribute("id", Arrays.toString(id));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupSetID(key, consumer, id);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long xgroupDestroy(byte[] key, byte[] consumer) {
    Span span = TracingHelper.buildSpan("Redis.XgroupDestroy");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("consumer", Arrays.toString(consumer));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupDestroy(key, consumer);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String xgroupDelConsumer(byte[] key, byte[] consumer, byte[] consumerName) {
    Span span = TracingHelper.buildSpan("Redis.XgroupDelConsumer");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("consumer", Arrays.toString(consumer));
    span.setAttribute("consumerName", Arrays.toString(consumerName));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xgroupDelConsumer(key, consumer, consumerName);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long xdel(byte[] key, byte[]... ids) {
    Span span = TracingHelper.buildSpan("Redis.Xdel");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("ids", TracingHelper.toString(ids));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xdel(key, ids);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long xtrim(byte[] key, long maxLen, boolean approximateLength) {
    Span span = TracingHelper.buildSpan("Redis.Xtrim");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("maxLen", maxLen);
    span.setAttribute("approximateLength", approximateLength);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xtrim(key, maxLen, approximateLength);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> xpending(
      byte[] key, byte[] groupname, byte[] start, byte[] end, int count, byte[] consumername) {
    Span span = TracingHelper.buildSpan("Redis.Xpending");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("groupname", Arrays.toString(groupname));
    span.setAttribute("start", Arrays.toString(start));
    span.setAttribute("end", Arrays.toString(end));
    span.setAttribute("count", count);
    span.setAttribute("consumername", Arrays.toString(consumername));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xpending(key, groupname, start, end, count, consumername);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> xclaim(
      byte[] key,
      byte[] groupname,
      byte[] consumername,
      long minIdleTime,
      long newIdleTime,
      int retries,
      boolean force,
      byte[][] ids) {
    Span span = TracingHelper.buildSpan("Redis.Xclaim");
    span.setAttribute("key", Arrays.toString(key));
    span.setAttribute("groupname", Arrays.toString(groupname));
    span.setAttribute("consumername", Arrays.toString(consumername));
    span.setAttribute("minIdleTime", minIdleTime);
    span.setAttribute("newIdleTime", newIdleTime);
    span.setAttribute("retries", retries);
    span.setAttribute("force", force);
    span.setAttribute("ids", TracingHelper.toString(ids));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.xclaim(
          key, groupname, consumername, minIdleTime, newIdleTime, retries, force, ids);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object sendCommand(ProtocolCommand cmd, byte[]... args) {
    Span span = TracingHelper.buildSpan("Redis.SendCommand");
    span.setAttribute("cmd", nullable(cmd));
    span.setAttribute("args", TracingHelper.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sendCommand(cmd, args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object sendCommand(ProtocolCommand cmd) {
    Span span = TracingHelper.buildSpan("Redis.SendCommand");
    span.setAttribute("cmd", nullable(cmd));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sendCommand(cmd);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String ping(String message) {
    Span span = TracingHelper.buildSpan("Redis.Ping");
    span.setAttribute("message", message);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ping(message);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String set(String key, String value) {
    Span span = TracingHelper.buildSpan("Redis.Set", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.set(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String set(String key, String value, SetParams params) {
    Span span = TracingHelper.buildSpan("Redis.Set", key);
    span.setAttribute("value", value);
    span.setAttribute("params", nullable(TracingHelper.toString(params.getByteParams())));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.set(key, value, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String get(String key) {
    Span span = TracingHelper.buildSpan("Redis.Get", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.get(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long exists(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Exists", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.exists(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean exists(String key) {
    Span span = TracingHelper.buildSpan("Redis.Exists", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.exists(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long del(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Del", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.del(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long del(String key) {
    Span span = TracingHelper.buildSpan("Redis.Del", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.del(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long unlink(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Unlink", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.unlink(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long unlink(String key) {
    Span span = TracingHelper.buildSpan("Redis.Unlink", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.unlink(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String type(String key) {
    Span span = TracingHelper.buildSpan("Redis.Type", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.type(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> keys(String pattern) {
    Span span = TracingHelper.buildSpan("Redis.Keys");
    span.setAttribute("pattern", nullable(pattern));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.keys(pattern);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String randomKey() {
    Span span = TracingHelper.buildSpan("Redis.RandomKey");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.randomKey();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String rename(String oldkey, String newkey) {
    Span span = TracingHelper.buildSpan("Redis.Rename");
    span.setAttribute("oldKey", nullable(oldkey));
    span.setAttribute("newKey", nullable(newkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rename(oldkey, newkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long renamenx(String oldkey, String newkey) {
    Span span = TracingHelper.buildSpan("Redis.Renamenx");
    span.setAttribute("oldKey", nullable(oldkey));
    span.setAttribute("newKey", nullable(newkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.renamenx(oldkey, newkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long expire(String key, int seconds) {
    Span span = TracingHelper.buildSpan("Redis.Expire", key);
    span.setAttribute("seconds", seconds);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.expire(key, seconds);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long expireAt(String key, long unixTime) {
    Span span = TracingHelper.buildSpan("Redis.ExpireAt", key);
    span.setAttribute("unixTime", unixTime);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.expireAt(key, unixTime);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long ttl(String key) {
    Span span = TracingHelper.buildSpan("Redis.Ttl", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ttl(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long touch(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Touch", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.touch(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long touch(String key) {
    Span span = TracingHelper.buildSpan("Redis.Touch", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.touch(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long move(String key, int dbIndex) {
    Span span = TracingHelper.buildSpan("Redis.Move", key);
    span.setAttribute("dbIndex", dbIndex);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.move(key, dbIndex);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String getSet(String key, String value) {
    Span span = TracingHelper.buildSpan("Redis.GetSet", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getSet(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> mget(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Mget", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.mget(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long setnx(String key, String value) {
    Span span = TracingHelper.buildSpan("Redis.Setnx", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setnx(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String setex(String key, int seconds, String value) {
    Span span = TracingHelper.buildSpan("Redis.Setex", key);
    span.setAttribute("seconds", seconds);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setex(key, seconds, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String mset(String... keysvalues) {
    Span span = TracingHelper.buildSpan("Redis.Mset");
    span.setAttribute("keysvalues", Arrays.toString(keysvalues));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.mset(keysvalues);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long msetnx(String... keysvalues) {
    Span span = TracingHelper.buildSpan("Redis.Msetnx");
    span.setAttribute("keysvalues", Arrays.toString(keysvalues));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.msetnx(keysvalues);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long decrBy(String key, long integer) {
    Span span = TracingHelper.buildSpan("Redis.DecrBy", key);
    span.setAttribute("integer", integer);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.decrBy(key, integer);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long decr(String key) {
    Span span = TracingHelper.buildSpan("Redis.Decr", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.decr(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long incrBy(String key, long integer) {
    Span span = TracingHelper.buildSpan("Redis.IncrBy", key);
    span.setAttribute("integer", integer);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.incrBy(key, integer);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double incrByFloat(String key, double value) {
    Span span = TracingHelper.buildSpan("Redis.IncrByFloat", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.incrByFloat(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long incr(String key) {
    Span span = TracingHelper.buildSpan("Redis.Incr", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.incr(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long append(String key, String value) {
    Span span = TracingHelper.buildSpan("Redis.Append", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.append(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String substr(String key, int start, int end) {
    Span span = TracingHelper.buildSpan("Redis.Substr", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.substr(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hset(String key, String field, String value) {
    Span span = TracingHelper.buildSpan("Redis.Hset", key);
    span.setAttribute("field", field);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hset(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hset(String key, Map<String, String> hash) {
    Span span = TracingHelper.buildSpan("Redis.Hset", key);
    span.setAttribute("hash", TracingHelper.toString(hash));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hset(key, hash);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String hget(String key, String field) {
    Span span = TracingHelper.buildSpan("Redis.Hget", key);
    span.setAttribute("field", field);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hget(key, field);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hsetnx(String key, String field, String value) {
    Span span = TracingHelper.buildSpan("Redis.Hsetnx", key);
    span.setAttribute("field", field);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hsetnx(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String hmset(String key, Map<String, String> hash) {
    Span span = TracingHelper.buildSpan("Redis.Hmset", key);
    span.setAttribute("hash", TracingHelper.toString(hash));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hmset(key, hash);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> hmget(String key, String... fields) {
    Span span = TracingHelper.buildSpan("Redis.Hmget", key);
    span.setAttribute("fields", Arrays.toString(fields));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hmget(key, fields);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hincrBy(String key, String field, long value) {
    Span span = TracingHelper.buildSpan("Redis.HincrBy", key);
    span.setAttribute("field", field);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hincrBy(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double hincrByFloat(String key, String field, double value) {
    Span span = TracingHelper.buildSpan("Redis.HincrByFloat", key);
    span.setAttribute("field", field);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hincrByFloat(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean hexists(String key, String field) {
    Span span = TracingHelper.buildSpan("Redis.Hexists", key);
    span.setAttribute("field", field);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hexists(key, field);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hdel(String key, String... fields) {
    Span span = TracingHelper.buildSpan("Redis.Hdel", key);
    span.setAttribute("fields", Arrays.toString(fields));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hdel(key, fields);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hlen(String key) {
    Span span = TracingHelper.buildSpan("Redis.Hlen", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hlen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> hkeys(String key) {
    Span span = TracingHelper.buildSpan("Redis.Hkeys", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hkeys(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> hvals(String key) {
    Span span = TracingHelper.buildSpan("Redis.Hvals", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hvals(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Map<String, String> hgetAll(String key) {
    Span span = TracingHelper.buildSpan("Redis.HgetAll", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hgetAll(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long rpush(String key, String... strings) {
    Span span = TracingHelper.buildSpan("Redis.Rpush", key);
    span.setAttribute("strings", Arrays.toString(strings));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpush(key, strings);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lpush(String key, String... strings) {
    Span span = TracingHelper.buildSpan("Redis.Lpush", key);
    span.setAttribute("strings", Arrays.toString(strings));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lpush(key, strings);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long llen(String key) {
    Span span = TracingHelper.buildSpan("Redis.Llen", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.llen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> lrange(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Lrange", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lrange(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String ltrim(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Ltrim", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ltrim(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String lindex(String key, long index) {
    Span span = TracingHelper.buildSpan("Redis.Lindex", key);
    span.setAttribute("index", index);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lindex(key, index);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String lset(String key, long index, String value) {
    Span span = TracingHelper.buildSpan("Redis.Lset", key);
    span.setAttribute("index", index);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lset(key, index, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lrem(String key, long count, String value) {
    Span span = TracingHelper.buildSpan("Redis.Lrem", key);
    span.setAttribute("count", count);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lrem(key, count, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String lpop(String key) {
    Span span = TracingHelper.buildSpan("Redis.Lpop", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lpop(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String rpop(String key) {
    Span span = TracingHelper.buildSpan("Redis.Rpop", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpop(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String rpoplpush(String srckey, String dstkey) {
    Span span = TracingHelper.buildSpan("Redis.Rpoplpush");
    span.setAttribute("srckey", srckey);
    span.setAttribute("dstkey", dstkey);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpoplpush(srckey, dstkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sadd(String key, String... members) {
    Span span = TracingHelper.buildSpan("Redis.Sadd", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sadd(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> smembers(String key) {
    Span span = TracingHelper.buildSpan("Redis.Smembers", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.smembers(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long srem(String key, String... members) {
    Span span = TracingHelper.buildSpan("Redis.Srem", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.srem(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String spop(String key) {
    Span span = TracingHelper.buildSpan("Redis.Spop", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.spop(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> spop(String key, long count) {
    Span span = TracingHelper.buildSpan("Redis.Spop", key);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.spop(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long smove(String srckey, String dstkey, String member) {
    Span span = TracingHelper.buildSpan("Redis.Smove");
    span.setAttribute("srckey", srckey);
    span.setAttribute("dstkey", dstkey);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.smove(srckey, dstkey, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long scard(String key) {
    Span span = TracingHelper.buildSpan("Redis.Scard", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scard(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean sismember(String key, String member) {
    Span span = TracingHelper.buildSpan("Redis.Sismember", key);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sismember(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> sinter(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sinter", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sinter(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sinterstore(String dstkey, String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sinterstore", keys);
    span.setAttribute("dstkey", dstkey);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sinterstore(dstkey, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> sunion(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sunion", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sunion(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sunionstore(String dstkey, String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sunionstore", keys);
    span.setAttribute("dstkey", dstkey);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sunionstore(dstkey, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> sdiff(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sdiff", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sdiff(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sdiffstore(String dstkey, String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sdiffstore", keys);
    span.setAttribute("dstkey", dstkey);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sdiffstore(dstkey, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String srandmember(String key) {
    Span span = TracingHelper.buildSpan("Redis.Srandmember", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.srandmember(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> srandmember(String key, int count) {
    Span span = TracingHelper.buildSpan("Redis.Srandmember", key);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.srandmember(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(String key, double score, String member) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("score", score);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, score, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(String key, double score, String member, ZAddParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("score", score);
    span.setAttribute("member", member);
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, score, member, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("scoreMembers", TracingHelper.toString(scoreMembers));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, scoreMembers);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("scoreMembers", TracingHelper.toString(scoreMembers));
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, scoreMembers, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrange(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Zrange", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrange(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zrem(String key, String... members) {
    Span span = TracingHelper.buildSpan("Redis.Zrem", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrem(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double zincrby(String key, double score, String member) {
    Span span = TracingHelper.buildSpan("Redis.Zincrby", key);
    span.setAttribute("score", score);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zincrby(key, score, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double zincrby(String key, double increment, String member, ZIncrByParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zincrby", key);
    span.setAttribute("increment", increment);
    span.setAttribute("member", member);
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zincrby(key, increment, member, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zrank(String key, String member) {
    Span span = TracingHelper.buildSpan("Redis.Zrank", key);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrank(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zrevrank(String key, String member) {
    Span span = TracingHelper.buildSpan("Redis.Zrevrank", key);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrank(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrange(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Zrevrange", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrange(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeWithScores(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeWithScores", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeWithScores(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeWithScores", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeWithScores(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zcard(String key) {
    Span span = TracingHelper.buildSpan("Redis.Zcard", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zcard(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double zscore(String key, String member) {
    Span span = TracingHelper.buildSpan("Redis.Zscore", key);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zscore(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String watch(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Watch", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.watch(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> sort(String key) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> sort(String key, SortingParams sortingParameters) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    span.setAttribute("sortingParameters", TracingHelper.toString(sortingParameters.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key, sortingParameters);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> blpop(int timeout, String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Blpop", keys);
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.blpop(timeout, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> blpop(String... args) {
    Span span = TracingHelper.buildSpan("Redis.Blpop");
    span.setAttribute("args", Arrays.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.blpop(args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> brpop(String... args) {
    Span span = TracingHelper.buildSpan("Redis.Brpop");
    span.setAttribute("args", Arrays.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpop(args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sort(String key, SortingParams sortingParameters, String dstkey) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    span.setAttribute("sortingParameters", TracingHelper.toString(sortingParameters.getParams()));
    span.setAttribute("dstkey", dstkey);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key, sortingParameters, dstkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sort(String key, String dstkey) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    span.setAttribute("dstkey", dstkey);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key, dstkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> brpop(int timeout, String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Brpop", keys);
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpop(timeout, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zcount(String key, double min, double max) {
    Span span = TracingHelper.buildSpan("Redis.Zcount", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zcount(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zcount(String key, String min, String max) {
    Span span = TracingHelper.buildSpan("Redis.Zcount", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zcount(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, double min, double max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, String min, String max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(
      String key, double min, double max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(
      String key, String min, String max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, double max, double min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("max", max);
    span.setAttribute("min", min);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, String max, String min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("max", max);
    span.setAttribute("min", min);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("max", max);
    span.setAttribute("min", min);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(
      String key, double max, double min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(
      String key, String max, String min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("max", max);
    span.setAttribute("min", min);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByRank(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByRank", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByRank(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByScore(String key, double start, double end) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByScore", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByScore(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByScore(String key, String start, String end) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByScore", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByScore(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zunionstore(String dstkey, String... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zunionstore");
    span.setAttribute("dstkey", dstkey);
    span.setAttribute("sets", Arrays.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zunionstore(dstkey, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zunionstore(String dstkey, ZParams params, String... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zunionstore");
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    span.setAttribute("sets", Arrays.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zunionstore(dstkey, params, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zinterstore(String dstkey, String... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zinterstore");
    span.setAttribute("dstkey", dstkey);
    span.setAttribute("sets", Arrays.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zinterstore(dstkey, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zinterstore(String dstkey, ZParams params, String... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zinterstore");
    span.setAttribute("dstkey", dstkey);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    span.setAttribute("sets", Arrays.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zinterstore(dstkey, params, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zlexcount(String key, String min, String max) {
    Span span = TracingHelper.buildSpan("Redis.Zlexcount", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zlexcount(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrangeByLex(String key, String min, String max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByLex", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByLex(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByLex", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByLex(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrangeByLex(String key, String max, String min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByLex", key);
    span.setAttribute("max", max);
    span.setAttribute("min", min);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByLex(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByLex", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByLex(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByLex(String key, String min, String max) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByLex", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByLex(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long strlen(String key) {
    Span span = TracingHelper.buildSpan("Redis.Strlen", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.strlen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lpushx(String key, String... string) {
    Span span = TracingHelper.buildSpan("Redis.Lpushx", key);
    span.setAttribute("string", Arrays.toString(string));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lpushx(key, string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long persist(String key) {
    Span span = TracingHelper.buildSpan("Redis.Persist", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.persist(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long rpushx(String key, String... string) {
    Span span = TracingHelper.buildSpan("Redis.Rpushx", key);
    span.setAttribute("string", Arrays.toString(string));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpushx(key, string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String echo(String string) {
    Span span = TracingHelper.buildSpan("Redis.Echo");
    span.setAttribute("string", string);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.echo(string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long linsert(String key, ListPosition where, String pivot, String value) {
    Span span = TracingHelper.buildSpan("Redis.Linsert", key);
    span.setAttribute("where", nullable(where));
    span.setAttribute("pivot", pivot);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.linsert(key, where, pivot, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String brpoplpush(String source, String destination, int timeout) {
    Span span = TracingHelper.buildSpan("Redis.Brpoplpush");
    span.setAttribute("source", source);
    span.setAttribute("destination", destination);
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpoplpush(source, destination, timeout);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean setbit(String key, long offset, boolean value) {
    Span span = TracingHelper.buildSpan("Redis.Setbit", key);
    span.setAttribute("offset", offset);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setbit(key, offset, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean setbit(String key, long offset, String value) {
    Span span = TracingHelper.buildSpan("Redis.Setbit", key);
    span.setAttribute("offset", offset);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setbit(key, offset, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean getbit(String key, long offset) {
    Span span = TracingHelper.buildSpan("Redis.Getbit", key);
    span.setAttribute("offset", offset);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getbit(key, offset);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long setrange(String key, long offset, String value) {
    Span span = TracingHelper.buildSpan("Redis.Setrange", key);
    span.setAttribute("offset", offset);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setrange(key, offset, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String getrange(String key, long startOffset, long endOffset) {
    Span span = TracingHelper.buildSpan("Redis.Getrange", key);
    span.setAttribute("startOffset", startOffset);
    span.setAttribute("endOffset", endOffset);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getrange(key, startOffset, endOffset);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitpos(String key, boolean value) {
    Span span = TracingHelper.buildSpan("Redis.Bitpos", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitpos(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitpos(String key, boolean value, BitPosParams params) {
    Span span = TracingHelper.buildSpan("Redis.Bitpos", key);
    span.setAttribute("value", value);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitpos(key, value, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> configGet(String pattern) {
    Span span = TracingHelper.buildSpan("Redis.ConfigGet");
    span.setAttribute("pattern", pattern);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.configGet(pattern);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String configSet(String parameter, String value) {
    Span span = TracingHelper.buildSpan("Redis.ConfigSet");
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.configSet(parameter, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object eval(String script, int keyCount, String... params) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("keyCount", keyCount);
    span.setAttribute("params", Arrays.toString(params));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script, keyCount, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void subscribe(JedisPubSub jedisPubSub, String... channels) {
    Span span = TracingHelper.buildSpan("Redis.Subscribe");
    span.setAttribute("channels", Arrays.toString(channels));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.subscribe(jedisPubSub, channels);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long publish(String channel, String message) {
    Span span = TracingHelper.buildSpan("Redis.Publish");
    span.setAttribute("channel", channel);
    span.setAttribute("message", message);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.publish(channel, message);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
    Span span = TracingHelper.buildSpan("Redis.Psubscribe");
    span.setAttribute("patterns", Arrays.toString(patterns));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.psubscribe(jedisPubSub, patterns);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object eval(String script, List<String> keys, List<String> args) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("keys", TracingHelper.toString(keys));
    span.setAttribute("args", TracingHelper.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script, keys, args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object eval(String script) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("script", script);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object evalsha(String script) {
    Span span = TracingHelper.buildSpan("Redis.Evalsha");
    span.setAttribute("script", script);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.evalsha(script);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object evalsha(String sha1, List<String> keys, List<String> args) {
    Span span = TracingHelper.buildSpan("Redis.Evalsha");
    span.setAttribute("keys", TracingHelper.toString(keys));
    span.setAttribute("args", TracingHelper.toString(args));
    span.setAttribute("sha1", sha1);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.evalsha(sha1, keys, args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object evalsha(String sha1, int keyCount, String... params) {
    Span span = TracingHelper.buildSpan("Redis.Evalsha");
    span.setAttribute("keyCount", keyCount);
    span.setAttribute("params", Arrays.toString(params));
    span.setAttribute("sha1", sha1);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.evalsha(sha1, keyCount, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean scriptExists(String sha1) {
    Span span = TracingHelper.buildSpan("Redis.ScriptExists");
    span.setAttribute("sha1", sha1);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptExists(sha1);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Boolean> scriptExists(String... sha1) {
    Span span = TracingHelper.buildSpan("Redis.ScriptExists");
    span.setAttribute("sha1", Arrays.toString(sha1));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptExists(sha1);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String scriptLoad(String script) {
    Span span = TracingHelper.buildSpan("Redis.ScriptLoad");
    span.setAttribute("script", script);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptLoad(script);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Slowlog> slowlogGet() {
    Span span = TracingHelper.buildSpan("Redis.SlowlogGet");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slowlogGet();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Slowlog> slowlogGet(long entries) {
    Span span = TracingHelper.buildSpan("Redis.SlowlogGet");
    span.setAttribute("entries", entries);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slowlogGet(entries);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long objectRefcount(String string) {
    Span span = TracingHelper.buildSpan("Redis.ObjectRefcount");
    span.setAttribute("string", string);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.objectRefcount(string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String objectEncoding(String string) {
    Span span = TracingHelper.buildSpan("Redis.ObjectEncoding");
    span.setAttribute("string", string);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.objectEncoding(string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long objectIdletime(String string) {
    Span span = TracingHelper.buildSpan("Redis.ObjectIdletime");
    span.setAttribute("string", string);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.objectIdletime(string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitcount(String key) {
    Span span = TracingHelper.buildSpan("Redis.Bitcount", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitcount(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitcount(String key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Bitcount", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitcount(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitop(BitOP op, String destKey, String... srcKeys) {
    Span span = TracingHelper.buildSpan("Redis.Bitop");
    span.setAttribute("destKey", destKey);
    span.setAttribute("srcKeys", Arrays.toString(srcKeys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitop(op, destKey, srcKeys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Map<String, String>> sentinelMasters() {
    Span span = TracingHelper.buildSpan("Redis.SentinelMasters");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelMasters();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> sentinelGetMasterAddrByName(String masterName) {
    Span span = TracingHelper.buildSpan("Redis.SentinelGetMasterAddrByName");
    span.setAttribute("masterName", masterName);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelGetMasterAddrByName(masterName);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sentinelReset(String pattern) {
    Span span = TracingHelper.buildSpan("Redis.SentinelReset");
    span.setAttribute("pattern", pattern);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelReset(pattern);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Map<String, String>> sentinelSlaves(String masterName) {
    Span span = TracingHelper.buildSpan("Redis.SentinelSlaves");
    span.setAttribute("masterName", masterName);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelSlaves(masterName);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String sentinelFailover(String masterName) {
    Span span = TracingHelper.buildSpan("Redis.SentinelFailover");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelFailover(masterName);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String sentinelMonitor(String masterName, String ip, int port, int quorum) {
    Span span = TracingHelper.buildSpan("Redis.SentinelMonitor");
    span.setAttribute("masterName", masterName);
    span.setAttribute("ip", ip);
    span.setAttribute("port", port);
    span.setAttribute("quorum", quorum);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelMonitor(masterName, ip, port, quorum);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String sentinelRemove(String masterName) {
    Span span = TracingHelper.buildSpan("Redis.SentinelRemove");
    span.setAttribute("masterName", masterName);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelRemove(masterName);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String sentinelSet(String masterName, Map<String, String> parameterMap) {
    Span span = TracingHelper.buildSpan("Redis.SentinelSet");
    span.setAttribute("masterName", masterName);
    span.setAttribute("parameterMap", TracingHelper.toString(parameterMap));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sentinelSet(masterName, parameterMap);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] dump(String key) {
    Span span = TracingHelper.buildSpan("Redis.Dump", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.dump(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String restore(String key, int ttl, byte[] serializedValue) {
    Span span = TracingHelper.buildSpan("Redis.Restore", key);
    span.setAttribute("ttl", ttl);
    span.setAttribute("serializedValue", Arrays.toString(serializedValue));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.restore(key, ttl, serializedValue);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String restoreReplace(String key, int ttl, byte[] serializedValue) {
    Span span = TracingHelper.buildSpan("Redis.RestoreReplace", key);
    span.setAttribute("ttl", ttl);
    span.setAttribute("serializedValue", Arrays.toString(serializedValue));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.restoreReplace(key, ttl, serializedValue);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pexpire(String key, long milliseconds) {
    Span span = TracingHelper.buildSpan("Redis.Pexpire", key);
    span.setAttribute("milliseconds", milliseconds);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pexpire(key, milliseconds);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pexpireAt(String key, long millisecondsTimestamp) {
    Span span = TracingHelper.buildSpan("Redis.PexpireAt", key);
    span.setAttribute("millisecondsTimestamp", millisecondsTimestamp);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pexpireAt(key, millisecondsTimestamp);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pttl(String key) {
    Span span = TracingHelper.buildSpan("Redis.Pttl", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pttl(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String psetex(String key, long milliseconds, String value) {
    Span span = TracingHelper.buildSpan("Redis.Psetex", key);
    span.setAttribute("value", value);
    span.setAttribute("milliseconds", milliseconds);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.psetex(key, milliseconds, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientKill(String client) {
    Span span = TracingHelper.buildSpan("Redis.ClientKill");
    span.setAttribute("client", client);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientKill(client);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientGetname() {
    Span span = TracingHelper.buildSpan("Redis.ClientGetname");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientGetname();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientList() {
    Span span = TracingHelper.buildSpan("Redis.ClientList");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientList();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientSetname(String name) {
    Span span = TracingHelper.buildSpan("Redis.ClientSetname");
    span.setAttribute("name", name);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientSetname(name);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String migrate(String host, int port, String key, int destinationDb, int timeout) {
    Span span = TracingHelper.buildSpan("Redis.Migrate", key);
    span.setAttribute("host", host);
    span.setAttribute("destinationDb", destinationDb);
    span.setAttribute("timeout", timeout);
    span.setAttribute("port", port);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.migrate(host, port, key, destinationDb, timeout);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String migrate(
      String host, int port, int destinationDB, int timeout, MigrateParams params, String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Migrate", keys);
    span.setAttribute("host", host);
    span.setAttribute("destinationDB", destinationDB);
    span.setAttribute("timeout", timeout);
    span.setAttribute("port", port);
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.migrate(host, port, destinationDB, timeout, params, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<String> scan(String cursor) {
    Span span = TracingHelper.buildSpan("Redis.Scan");
    span.setAttribute("cursor", cursor);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scan(cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<String> scan(String cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Scan");
    span.setAttribute("cursor", cursor);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scan(cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
    Span span = TracingHelper.buildSpan("Redis.Hscan", key);
    span.setAttribute("cursor", cursor);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hscan(key, cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Hscan", key);
    span.setAttribute("cursor", cursor);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hscan(key, cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<String> sscan(String key, String cursor) {
    Span span = TracingHelper.buildSpan("Redis.Sscan", key);
    span.setAttribute("cursor", cursor);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sscan(key, cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Sscan", key);
    span.setAttribute("cursor", cursor);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sscan(key, cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Tuple> zscan(String key, String cursor) {
    Span span = TracingHelper.buildSpan("Redis.Zscan", key);
    span.setAttribute("cursor", cursor);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zscan(key, cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zscan", key);
    span.setAttribute("cursor", cursor);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zscan(key, cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterNodes() {
    Span span = TracingHelper.buildSpan("Redis.ClusterNodes");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterNodes();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String readonly() {
    Span span = TracingHelper.buildSpan("Redis.Readonly");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.readonly();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterMeet(String ip, int port) {
    Span span = TracingHelper.buildSpan("Redis.ClusterMeet");
    span.setAttribute("ip", ip);
    span.setAttribute("port", port);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterMeet(ip, port);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterReset(ClusterReset resetType) {
    Span span = TracingHelper.buildSpan("Redis.ClusterReset");
    span.setAttribute("resetType", resetType.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterReset(resetType);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterAddSlots(int... slots) {
    Span span = TracingHelper.buildSpan("Redis.ClusterAddSlots");
    span.setAttribute("slots", Arrays.toString(slots));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterAddSlots(slots);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterDelSlots(int... slots) {
    Span span = TracingHelper.buildSpan("Redis.ClusterDelSlots");
    span.setAttribute("slots", Arrays.toString(slots));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterDelSlots(slots);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterInfo() {
    Span span = TracingHelper.buildSpan("Redis.ClusterInfo");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterInfo();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> clusterGetKeysInSlot(int slot, int count) {
    Span span = TracingHelper.buildSpan("Redis.ClusterGetKeysInSlot");
    span.setAttribute("slot", slot);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterGetKeysInSlot(slot, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterSetSlotNode(int slot, String nodeId) {
    Span span = TracingHelper.buildSpan("Redis.ClusterSetSlotNode");
    span.setAttribute("slot", slot);
    span.setAttribute("nodeId", nodeId);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSetSlotNode(slot, nodeId);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterSetSlotMigrating(int slot, String nodeId) {
    Span span = TracingHelper.buildSpan("Redis.ClusterSetSlotMigrating");
    span.setAttribute("slot", slot);
    span.setAttribute("nodeId", nodeId);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSetSlotMigrating(slot, nodeId);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterSetSlotImporting(int slot, String nodeId) {
    Span span = TracingHelper.buildSpan("Redis.ClusterSetSlotImporting");
    span.setAttribute("slot", slot);
    span.setAttribute("nodeId", nodeId);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSetSlotImporting(slot, nodeId);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterSetSlotStable(int slot) {
    Span span = TracingHelper.buildSpan("Redis.ClusterSetSlotStable");
    span.setAttribute("slot", slot);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSetSlotStable(slot);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterForget(String nodeId) {
    Span span = TracingHelper.buildSpan("Redis.ClusterForget");
    span.setAttribute("nodeId", nodeId);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterForget(nodeId);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterFlushSlots() {
    Span span = TracingHelper.buildSpan("Redis.ClusterFlushSlots");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterFlushSlots();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long clusterKeySlot(String key) {
    Span span = TracingHelper.buildSpan("Redis.ClusterKeySlot", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterKeySlot(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long clusterCountKeysInSlot(int slot) {
    Span span = TracingHelper.buildSpan("Redis.ClusterCountKeysInSlot");
    span.setAttribute("slot", slot);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterCountKeysInSlot(slot);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterSaveConfig() {
    Span span = TracingHelper.buildSpan("Redis.ClusterSaveConfig");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSaveConfig();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterReplicate(String nodeId) {
    Span span = TracingHelper.buildSpan("Redis.ClusterReplicate");
    span.setAttribute("nodeId", nodeId);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterReplicate(nodeId);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> clusterSlaves(String nodeId) {
    Span span = TracingHelper.buildSpan("Redis.ClusterSlaves");
    span.setAttribute("nodeId", nodeId);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSlaves(nodeId);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clusterFailover() {
    Span span = TracingHelper.buildSpan("Redis.ClusterFailover");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterFailover();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Object> clusterSlots() {
    Span span = TracingHelper.buildSpan("Redis.ClusterSlots");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clusterSlots();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String asking() {
    Span span = TracingHelper.buildSpan("Redis.Asking");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.asking();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> pubsubChannels(String pattern) {
    Span span = TracingHelper.buildSpan("Redis.PubsubChannels");
    span.setAttribute("pattern", pattern);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pubsubChannels(pattern);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pubsubNumPat() {
    Span span = TracingHelper.buildSpan("Redis.PubsubNumPat");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pubsubNumPat();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Map<String, String> pubsubNumSub(String... channels) {
    Span span = TracingHelper.buildSpan("Redis.PubsubNumSub");
    span.setAttribute("channels", Arrays.toString(channels));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pubsubNumSub(channels);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void close() {
    super.close();
  }

  @Override
  public void setDataSource(JedisPoolAbstract jedisPool) {
    // OT decoration is not needed
    super.setDataSource(jedisPool);
  }

  @Override
  public Long pfadd(String key, String... elements) {
    Span span = TracingHelper.buildSpan("Redis.Pfadd");
    span.setAttribute("elements", Arrays.toString(elements));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfadd(key, elements);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long pfcount(String key) {
    Span span = TracingHelper.buildSpan("Redis.Pfcount", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfcount(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long pfcount(String... keys) {
    Span span = TracingHelper.buildSpan("Redis.Pfcount", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfcount(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String pfmerge(String destkey, String... sourcekeys) {
    Span span = TracingHelper.buildSpan("Redis.Pfmerge");
    span.setAttribute("destkey", destkey);
    span.setAttribute("sourcekeys", Arrays.toString(sourcekeys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfmerge(destkey, sourcekeys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> blpop(int timeout, String key) {
    Span span = TracingHelper.buildSpan("Redis.Blpop");
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.blpop(timeout, key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> brpop(int timeout, String key) {
    Span span = TracingHelper.buildSpan("Redis.Brpop");
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpop(timeout, key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long geoadd(String key, double longitude, double latitude, String member) {
    Span span = TracingHelper.buildSpan("Redis.Geoadd");
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("member", member);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geoadd(key, longitude, latitude, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
    Span span = TracingHelper.buildSpan("Redis.Geoadd");
    span.setAttribute("memberCoordinateMap", TracingHelper.toString(memberCoordinateMap));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geoadd(key, memberCoordinateMap);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double geodist(String key, String member1, String member2) {
    Span span = TracingHelper.buildSpan("Redis.Geodist", key);
    span.setAttribute("member1", member1);
    span.setAttribute("member2", member2);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geodist(key, member1, member2);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double geodist(String key, String member1, String member2, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.Geodist", key);
    span.setAttribute("member1", member1);
    span.setAttribute("member2", member2);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geodist(key, member1, member2, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> geohash(String key, String... members) {
    Span span = TracingHelper.buildSpan("Redis.Geohash", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geohash(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoCoordinate> geopos(String key, String... members) {
    Span span = TracingHelper.buildSpan("Redis.Geopos", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geopos(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadius(
      String key, double longitude, double latitude, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.Georadius", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadius(key, longitude, latitude, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusReadonly(
      String key, double longitude, double latitude, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusReadonly", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusReadonly(key, longitude, latitude, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadius(
      String key,
      double longitude,
      double latitude,
      double radius,
      GeoUnit unit,
      GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.Georadius", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadius(key, longitude, latitude, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusReadonly(
      String key,
      double longitude,
      double latitude,
      double radius,
      GeoUnit unit,
      GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusReadonly", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusReadonly(key, longitude, latitude, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMember(
      String key, String member, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMember", key);
    span.setAttribute("member", member);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMember(key, member, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMemberReadonly(
      String key, String member, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMemberReadonly", key);
    span.setAttribute("member", member);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMemberReadonly(key, member, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMember(
      String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMember", key);
    span.setAttribute("member", member);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMember(key, member, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMemberReadonly(
      String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMemberReadonly", key);
    span.setAttribute("member", member);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMemberReadonly(key, member, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String moduleLoad(String path) {
    Span span = TracingHelper.buildSpan("Redis.ModuleLoad");
    span.setAttribute("path", path);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.moduleLoad(path);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String moduleUnload(String name) {
    Span span = TracingHelper.buildSpan("Redis.ModuleUnload");
    span.setAttribute("name", name);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.moduleUnload(name);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Module> moduleList() {
    Span span = TracingHelper.buildSpan("Redis.ModuleList");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.moduleList();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Long> bitfield(String key, String... arguments) {
    Span span = TracingHelper.buildSpan("Redis.Bitfield", key);
    span.setAttribute("arguments", Arrays.toString(arguments));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitfield(key, arguments);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hstrlen(String key, String field) {
    Span span = TracingHelper.buildSpan("Redis.Hstrlen", key);
    span.setAttribute("field", field);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hstrlen(key, field);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String ping() {
    Span span = TracingHelper.buildSpan("Redis.Ping");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ping();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] ping(byte[] message) {
    Span span = TracingHelper.buildSpan("Redis.Ping");
    span.setAttribute("message", Arrays.toString(message));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ping(message);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String set(byte[] key, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Set", key);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.set(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String set(byte[] key, byte[] value, SetParams params) {
    Span span = TracingHelper.buildSpan("Redis.Set", key);
    span.setAttribute("value", Arrays.toString(value));
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.set(key, value, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] get(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Get", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.get(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String quit() {
    Span span = TracingHelper.buildSpan("Redis.Quit");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.quit();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long exists(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Exists");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.exists(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean exists(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Exists", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.exists(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long del(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Del");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.del(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long del(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Del", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.del(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long unlink(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Unlink", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.unlink(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long unlink(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Unlink", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.unlink(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String type(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Type", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.type(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String flushDB() {
    Span span = TracingHelper.buildSpan("Redis.FlushDB");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.flushDB();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> keys(byte[] pattern) {
    Span span = TracingHelper.buildSpan("Redis.Keys");
    span.setAttribute("pattern", Arrays.toString(pattern));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.keys(pattern);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] randomBinaryKey() {
    Span span = TracingHelper.buildSpan("Redis.RandomBinaryKey");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.randomBinaryKey();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String rename(byte[] oldkey, byte[] newkey) {
    Span span = TracingHelper.buildSpan("Redis.Rename");
    span.setAttribute("oldkey", Arrays.toString(oldkey));
    span.setAttribute("newkey", Arrays.toString(newkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rename(oldkey, newkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long renamenx(byte[] oldkey, byte[] newkey) {
    Span span = TracingHelper.buildSpan("Redis.Renamenx");
    span.setAttribute("oldkey", Arrays.toString(oldkey));
    span.setAttribute("newkey", Arrays.toString(newkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.renamenx(oldkey, newkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long dbSize() {
    Span span = TracingHelper.buildSpan("Redis.DbSize");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.dbSize();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long expire(byte[] key, int seconds) {
    Span span = TracingHelper.buildSpan("Redis.Expire", key);
    span.setAttribute("seconds", seconds);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.expire(key, seconds);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long expireAt(byte[] key, long unixTime) {
    Span span = TracingHelper.buildSpan("Redis.ExpireAt", key);
    span.setAttribute("unixTime", unixTime);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.expireAt(key, unixTime);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long ttl(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Ttl", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ttl(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long touch(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Touch", keys);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.touch(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long touch(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Touch", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.touch(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String select(int index) {
    Span span = TracingHelper.buildSpan("Redis.Select");
    span.setAttribute("index", index);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.select(index);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String swapDB(int index1, int index2) {
    Span span = TracingHelper.buildSpan("Redis.SwapDB");
    span.setAttribute("index1", index1);
    span.setAttribute("index2", index2);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.swapDB(index1, index2);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long move(byte[] key, int dbIndex) {
    Span span = TracingHelper.buildSpan("Redis.Move", key);
    span.setAttribute("dbIndex", dbIndex);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.move(key, dbIndex);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String flushAll() {
    Span span = TracingHelper.buildSpan("Redis.FlushAll");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.flushAll();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] getSet(byte[] key, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.GetSet", key);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getSet(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> mget(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Mget");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.mget(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long setnx(byte[] key, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Setnx", key);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setnx(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String setex(byte[] key, int seconds, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Setex", key);
    span.setAttribute("value", Arrays.toString(value));
    span.setAttribute("seconds", seconds);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setex(key, seconds, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String mset(byte[]... keysvalues) {
    Span span = TracingHelper.buildSpan("Redis.Mset");
    span.setAttribute("keysvalues", TracingHelper.toString(keysvalues));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.mset(keysvalues);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long msetnx(byte[]... keysvalues) {
    Span span = TracingHelper.buildSpan("Redis.Msetnx");
    span.setAttribute("keysvalues", TracingHelper.toString(keysvalues));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.msetnx(keysvalues);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long decrBy(byte[] key, long integer) {
    Span span = TracingHelper.buildSpan("Redis.DecrBy", key);
    span.setAttribute("integer", integer);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.decrBy(key, integer);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long decr(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Decr", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.decr(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long incrBy(byte[] key, long integer) {
    Span span = TracingHelper.buildSpan("Redis.IncrBy", key);
    span.setAttribute("integer", integer);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.incrBy(key, integer);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double incrByFloat(byte[] key, double integer) {
    Span span = TracingHelper.buildSpan("Redis.IncrByFloat", key);
    span.setAttribute("integer", integer);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.incrByFloat(key, integer);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long incr(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Incr", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.incr(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long append(byte[] key, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Append", key);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.append(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] substr(byte[] key, int start, int end) {
    Span span = TracingHelper.buildSpan("Redis.Substr", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.substr(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hset(byte[] key, byte[] field, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Hset", key);
    span.setAttribute("field", Arrays.toString(field));
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hset(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hset(byte[] key, Map<byte[], byte[]> hash) {
    Span span = TracingHelper.buildSpan("Redis.Hset", key);
    span.setAttribute("hash", TracingHelper.toStringMapBytes(hash));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hset(key, hash);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] hget(byte[] key, byte[] field) {
    Span span = TracingHelper.buildSpan("Redis.Hget", key);
    span.setAttribute("field", Arrays.toString(field));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hget(key, field);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hsetnx(byte[] key, byte[] field, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Hsetnx", key);
    span.setAttribute("field", Arrays.toString(field));
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hsetnx(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String hmset(byte[] key, Map<byte[], byte[]> hash) {
    Span span = TracingHelper.buildSpan("Redis.Hmset", key);
    span.setAttribute("hash", TracingHelper.toStringMapBytes(hash));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hmset(key, hash);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> hmget(byte[] key, byte[]... fields) {
    Span span = TracingHelper.buildSpan("Redis.Hmget", key);
    span.setAttribute("fields", TracingHelper.toString(fields));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hmget(key, fields);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hincrBy(byte[] key, byte[] field, long value) {
    Span span = TracingHelper.buildSpan("Redis.HincrBy", key);
    span.setAttribute("field", Arrays.toString(field));
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hincrBy(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double hincrByFloat(byte[] key, byte[] field, double value) {
    Span span = TracingHelper.buildSpan("Redis.HincrByFloat", key);
    span.setAttribute("field", Arrays.toString(field));
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hincrByFloat(key, field, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean hexists(byte[] key, byte[] field) {
    Span span = TracingHelper.buildSpan("Redis.Hexists", key);
    span.setAttribute("field", Arrays.toString(field));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hexists(key, field);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hdel(byte[] key, byte[]... fields) {
    Span span = TracingHelper.buildSpan("Redis.Hdel", key);
    span.setAttribute("fields", TracingHelper.toString(fields));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hdel(key, fields);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hlen(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Hlen", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hlen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> hkeys(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Hkeys", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hkeys(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> hvals(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Hvals", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hvals(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Map<byte[], byte[]> hgetAll(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.HgetAll", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hgetAll(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long rpush(byte[] key, byte[]... strings) {
    Span span = TracingHelper.buildSpan("Redis.Rpush", key);
    span.setAttribute("strings", TracingHelper.toString(strings));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpush(key, strings);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lpush(byte[] key, byte[]... strings) {
    Span span = TracingHelper.buildSpan("Redis.Lpush", key);
    span.setAttribute("strings", TracingHelper.toString(strings));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lpush(key, strings);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long llen(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Llen", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.llen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> lrange(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Lrange", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lrange(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String ltrim(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Ltrim", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.ltrim(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] lindex(byte[] key, long index) {
    Span span = TracingHelper.buildSpan("Redis.Lindex", key);
    span.setAttribute("index", index);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lindex(key, index);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String lset(byte[] key, long index, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Lset", key);
    span.setAttribute("index", index);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lset(key, index, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lrem(byte[] key, long count, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Lrem", key);
    span.setAttribute("count", count);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lrem(key, count, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] lpop(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Lpop", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lpop(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] rpop(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Rpop", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpop(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] rpoplpush(byte[] srckey, byte[] dstkey) {
    Span span = TracingHelper.buildSpan("Redis.Rpoplpush");
    span.setAttribute("srckey", Arrays.toString(srckey));
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpoplpush(srckey, dstkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sadd(byte[] key, byte[]... members) {
    Span span = TracingHelper.buildSpan("Redis.Sadd", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sadd(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> smembers(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Smembers", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.smembers(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long srem(byte[] key, byte[]... member) {
    Span span = TracingHelper.buildSpan("Redis.Srem", key);
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.srem(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] spop(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Spop", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.spop(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> spop(byte[] key, long count) {
    Span span = TracingHelper.buildSpan("Redis.Spop", key);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.spop(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long smove(byte[] srckey, byte[] dstkey, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Smove");
    span.setAttribute("srckey", Arrays.toString(srckey));
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.smove(srckey, dstkey, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long scard(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Scard", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scard(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean sismember(byte[] key, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Sismember", key);
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sismember(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> sinter(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sinter");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sinter(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sinterstore(byte[] dstkey, byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sinterstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sinterstore(dstkey, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> sunion(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sunion");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sunion(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sunionstore(byte[] dstkey, byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sunionstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sunionstore(dstkey, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> sdiff(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sdiff");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sdiff(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sdiffstore(byte[] dstkey, byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Sdiffstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sdiffstore(dstkey, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] srandmember(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Srandmember", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.srandmember(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> srandmember(byte[] key, int count) {
    Span span = TracingHelper.buildSpan("Redis.Srandmember", key);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.srandmember(key, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(byte[] key, double score, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("score", score);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, score, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("score", score);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, score, member, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("scoreMembers", TracingHelper.toStringMapGeneric(scoreMembers));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, scoreMembers);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zadd", key);
    span.setAttribute("scoreMembers", TracingHelper.toStringMapGeneric(scoreMembers));
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zadd(key, scoreMembers, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrange(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Zrange", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrange(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zrem(byte[] key, byte[]... members) {
    Span span = TracingHelper.buildSpan("Redis.Zrem", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrem(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double zincrby(byte[] key, double score, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Zincrby", key);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("score", score);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zincrby(key, score, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zincrby", key);
    span.setAttribute("increment", increment);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zincrby(key, increment, member, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zrank(byte[] key, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Zrank", key);
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrank(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zrevrank(byte[] key, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Zrevrank", key);
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrank(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrange(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Zrevrange", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrange(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeWithScores", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeWithScores(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeWithScores", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeWithScores(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zcard(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Zcard", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zcard(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double zscore(byte[] key, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Zscore", key);
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zscore(key, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Transaction multi() {
    Span span = TracingHelper.buildSpan("Redis.Multi");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.multi();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void connect() {
    Span span = TracingHelper.buildSpan("Redis.Connect");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.connect();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void disconnect() {
    Span span = TracingHelper.buildSpan("Redis.Disconnect");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.disconnect();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void resetState() {
    Span span = TracingHelper.buildSpan("Redis.ResetState");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.resetState();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String watch(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Watch");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.watch(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String unwatch() {
    Span span = TracingHelper.buildSpan("Redis.Unwatch");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.unwatch();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> sort(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    span.setAttribute("sortingParameters", TracingHelper.toString(sortingParameters.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key, sortingParameters);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> blpop(int timeout, byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Blpop");
    span.setAttribute("timeout", timeout);
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.blpop(timeout, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sort(byte[] key, SortingParams sortingParameters, byte[] dstkey) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    span.setAttribute("sortingParameters", TracingHelper.toString(sortingParameters.getParams()));
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key, sortingParameters, dstkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long sort(byte[] key, byte[] dstkey) {
    Span span = TracingHelper.buildSpan("Redis.Sort", key);
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sort(key, dstkey);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> brpop(int timeout, byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Brpop");
    span.setAttribute("timeout", timeout);
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpop(timeout, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> blpop(byte[]... args) {
    Span span = TracingHelper.buildSpan("Redis.Blpop");
    span.setAttribute("args", TracingHelper.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.blpop(args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> brpop(byte[]... args) {
    Span span = TracingHelper.buildSpan("Redis.Brpop");
    span.setAttribute("args", TracingHelper.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpop(args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String auth(String password) {
    Span span = TracingHelper.buildSpan("Redis.Auth");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.auth(password);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Pipeline pipelined() {
    Span span = TracingHelper.buildSpan("Redis.Pipelined");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pipelined();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zcount(byte[] key, double min, double max) {
    Span span = TracingHelper.buildSpan("Redis.Zcount", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zcount(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zcount(byte[] key, byte[] min, byte[] max) {
    Span span = TracingHelper.buildSpan("Redis.Zcount", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zcount(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScore", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScore(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(
      byte[] key, double min, double max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(
      byte[] key, byte[] min, byte[] max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByScoreWithScores", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByScoreWithScores(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  protected Set<Tuple> getTupledSet() {
    Span span = TracingHelper.buildSpan("Redis.Gettupledset");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getTupledSet();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("min", Arrays.toString(min));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScore", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScore(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(
      byte[] key, double max, double min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("min", min);
    span.setAttribute("max", max);
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("min", Arrays.toString(min));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(
      byte[] key, byte[] max, byte[] min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByScoreWithScores", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByScoreWithScores(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByRank(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByRank", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByRank(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByScore(byte[] key, double start, double end) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByScore", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByScore(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByScore", key);
    span.setAttribute("start", Arrays.toString(start));
    span.setAttribute("end", Arrays.toString(end));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByScore(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zunionstore(byte[] dstkey, byte[]... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zunionstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("sets", TracingHelper.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zunionstore(dstkey, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zunionstore(byte[] dstkey, ZParams params, byte[]... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zunionstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    span.setAttribute("sets", TracingHelper.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zunionstore(dstkey, params, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zinterstore(byte[] dstkey, byte[]... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zinterstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("sets", TracingHelper.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zinterstore(dstkey, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zinterstore(byte[] dstkey, ZParams params, byte[]... sets) {
    Span span = TracingHelper.buildSpan("Redis.Zinterstore");
    span.setAttribute("dstkey", Arrays.toString(dstkey));
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    span.setAttribute("sets", TracingHelper.toString(sets));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zinterstore(dstkey, params, sets);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zlexcount(byte[] key, byte[] min, byte[] max) {
    Span span = TracingHelper.buildSpan("Redis.Zlexcount");
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zlexcount(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByLex", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByLex(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrangeByLex", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrangeByLex(key, min, max, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByLex", key);
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("min", Arrays.toString(min));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByLex(key, max, min);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
    Span span = TracingHelper.buildSpan("Redis.ZrevrangeByLex", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    span.setAttribute("offset", offset);
    span.setAttribute("count", count);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zrevrangeByLex(key, max, min, offset, count);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
    Span span = TracingHelper.buildSpan("Redis.ZremrangeByLex", key);
    span.setAttribute("min", Arrays.toString(min));
    span.setAttribute("max", Arrays.toString(max));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zremrangeByLex(key, min, max);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String save() {
    Span span = TracingHelper.buildSpan("Redis.Save");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.save();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String bgsave() {
    Span span = TracingHelper.buildSpan("Redis.Bgsave");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bgsave();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String bgrewriteaof() {
    Span span = TracingHelper.buildSpan("Redis.Bgrewriteaof");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bgrewriteaof();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lastsave() {
    Span span = TracingHelper.buildSpan("Redis.Lastsave");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lastsave();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String shutdown() {
    Span span = TracingHelper.buildSpan("Redis.Shutdown");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.shutdown();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String info() {
    Span span = TracingHelper.buildSpan("Redis.Info");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.info();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String info(String section) {
    Span span = TracingHelper.buildSpan("Redis.Info");
    span.setAttribute("section", section);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.info(section);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void monitor(JedisMonitor jedisMonitor) {
    Span span = TracingHelper.buildSpan("Redis.Monitor");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.monitor(jedisMonitor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String slaveof(String host, int port) {
    Span span = TracingHelper.buildSpan("Redis.Slaveof");
    span.setAttribute("host", host);
    span.setAttribute("port", port);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slaveof(host, port);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String slaveofNoOne() {
    Span span = TracingHelper.buildSpan("Redis.SlaveofNoOne");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slaveofNoOne();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> configGet(byte[] pattern) {
    Span span = TracingHelper.buildSpan("Redis.ConfigGet");
    span.setAttribute("pattern", Arrays.toString(pattern));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.configGet(pattern);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String configResetStat() {
    Span span = TracingHelper.buildSpan("Redis.ConfigResetStat");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.configResetStat();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String configRewrite() {
    return super.configRewrite();
  }

  @Override
  public byte[] configSet(byte[] parameter, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.ConfigSet");
    span.setAttribute("parameter", Arrays.toString(parameter));
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.configSet(parameter, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public boolean isConnected() {
    Span span = TracingHelper.buildSpan("Redis.IsConnected");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.isConnected();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long strlen(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Strlen", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.strlen(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void sync() {
    Span span = TracingHelper.buildSpan("Redis.Sync");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.sync();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long lpushx(byte[] key, byte[]... string) {
    Span span = TracingHelper.buildSpan("Redis.Lpushx", key);
    span.setAttribute("string", TracingHelper.toString(string));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.lpushx(key, string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long persist(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Persist", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.persist(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long rpushx(byte[] key, byte[]... string) {
    Span span = TracingHelper.buildSpan("Redis.Rpushx", key);
    span.setAttribute("string", TracingHelper.toString(string));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.rpushx(key, string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] echo(byte[] string) {
    Span span = TracingHelper.buildSpan("Redis.Echo");
    span.setAttribute("string", Arrays.toString(string));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.echo(string);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long linsert(byte[] key, ListPosition where, byte[] pivot, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Linsert", key);
    span.setAttribute("where", where.name());
    span.setAttribute("pivot", Arrays.toString(pivot));
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.linsert(key, where, pivot, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String debug(DebugParams params) {
    Span span = TracingHelper.buildSpan("Redis.Debug");
    span.setAttribute("params", Arrays.toString(params.getCommand()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.debug(params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Client getClient() {
    Span span = TracingHelper.buildSpan("Redis.GetClient");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getClient();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] brpoplpush(byte[] source, byte[] destination, int timeout) {
    Span span = TracingHelper.buildSpan("Redis.Brpoplpush");
    span.setAttribute("timeout", timeout);
    span.setAttribute("source", Arrays.toString(source));
    span.setAttribute("destination", Arrays.toString(destination));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.brpoplpush(source, destination, timeout);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean setbit(byte[] key, long offset, boolean value) {
    Span span = TracingHelper.buildSpan("Redis.Setbit", key);
    span.setAttribute("offset", offset);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setbit(key, offset, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean setbit(byte[] key, long offset, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Setbit", key);
    span.setAttribute("offset", offset);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setbit(key, offset, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Boolean getbit(byte[] key, long offset) {
    Span span = TracingHelper.buildSpan("Redis.Getbit", key);
    span.setAttribute("offset", offset);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getbit(key, offset);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitpos(byte[] key, boolean value) {
    Span span = TracingHelper.buildSpan("Redis.Bitpos", key);
    span.setAttribute("value", value);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitpos(key, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitpos(byte[] key, boolean value, BitPosParams params) {
    Span span = TracingHelper.buildSpan("Redis.Bitpos", key);
    span.setAttribute("value", value);
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitpos(key, value, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long setrange(byte[] key, long offset, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Setrange", key);
    span.setAttribute("offset", offset);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.setrange(key, offset, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] getrange(byte[] key, long startOffset, long endOffset) {
    Span span = TracingHelper.buildSpan("Redis.Getrange", key);
    span.setAttribute("startOffset", startOffset);
    span.setAttribute("endOffset", endOffset);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.getrange(key, startOffset, endOffset);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long publish(byte[] channel, byte[] message) {
    Span span = TracingHelper.buildSpan("Redis.Publish");
    span.setAttribute("channel", Arrays.toString(channel));
    span.setAttribute("message", Arrays.toString(message));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.publish(channel, message);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {
    Span span = TracingHelper.buildSpan("Redis.Subscribe");
    span.setAttribute("channels", Arrays.toString(channels));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.subscribe(jedisPubSub, channels);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[]... patterns) {
    Span span = TracingHelper.buildSpan("Redis.Psubscribe");
    span.setAttribute("patterns", Arrays.toString(patterns));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      super.psubscribe(jedisPubSub, patterns);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public int getDB() {
    // OT decoration is not needed.
    return super.getDB();
  }

  @Override
  public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("script", Arrays.toString(script));
    span.setAttribute("keys", TracingHelper.toString(keys));
    span.setAttribute("args", TracingHelper.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script, keys, args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object eval(byte[] script, byte[] keyCount, byte[]... params) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("script", Arrays.toString(script));
    span.setAttribute("keyCount", Arrays.toString(keyCount));
    span.setAttribute("params", TracingHelper.toString(params));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script, keyCount, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object eval(byte[] script, int keyCount, byte[]... params) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("script", Arrays.toString(script));
    span.setAttribute("keyCount", keyCount);
    span.setAttribute("params", TracingHelper.toString(params));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script, keyCount, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object eval(byte[] script) {
    Span span = TracingHelper.buildSpan("Redis.Eval");
    span.setAttribute("script", Arrays.toString(script));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.eval(script);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object evalsha(byte[] sha1) {
    Span span = TracingHelper.buildSpan("Redis.Evalsha");
    span.setAttribute("sha1", Arrays.toString(sha1));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.evalsha(sha1);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
    Span span = TracingHelper.buildSpan("Redis.Evalsha");
    span.setAttribute("sha1", Arrays.toString(sha1));
    span.setAttribute("keys", TracingHelper.toString(keys));
    span.setAttribute("args", TracingHelper.toString(args));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.evalsha(sha1, keys, args);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Object evalsha(byte[] sha1, int keyCount, byte[]... params) {
    Span span = TracingHelper.buildSpan("Redis.Evalsha");
    span.setAttribute("params", TracingHelper.toString(params));
    span.setAttribute("sha1", Arrays.toString(sha1));
    span.setAttribute("keyCount", keyCount);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.evalsha(sha1, keyCount, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String scriptFlush() {
    Span span = TracingHelper.buildSpan("Redis.ScriptFlush");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptFlush();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long scriptExists(byte[] sha1) {
    Span span = TracingHelper.buildSpan("Redis.ScriptExists");
    span.setAttribute("sha1", Arrays.toString(sha1));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptExists(sha1);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Long> scriptExists(byte[]... sha1) {
    Span span = TracingHelper.buildSpan("Redis.ScriptExists");
    span.setAttribute("sha1", TracingHelper.toString(sha1));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptExists(sha1);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] scriptLoad(byte[] script) {
    Span span = TracingHelper.buildSpan("Redis.ScriptLoad");
    span.setAttribute("script", Arrays.toString(script));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptLoad(script);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String scriptKill() {
    Span span = TracingHelper.buildSpan("Redis.ScriptKill");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scriptKill();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String slowlogReset() {
    Span span = TracingHelper.buildSpan("Redis.SlowlogReset");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slowlogReset();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long slowlogLen() {
    Span span = TracingHelper.buildSpan("Redis.SlowlogLen");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slowlogLen();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> slowlogGetBinary() {
    Span span = TracingHelper.buildSpan("Redis.SlowlogGetBinary");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slowlogGetBinary();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> slowlogGetBinary(long entries) {
    Span span = TracingHelper.buildSpan("Redis.SlowlogGetBinary");
    span.setAttribute("entries", entries);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.slowlogGetBinary(entries);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long objectRefcount(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.ObjectRefcount", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.objectRefcount(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] objectEncoding(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.ObjectEncoding", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.objectEncoding(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long objectIdletime(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.ObjectIdletime", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.objectIdletime(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitcount(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Bitcount", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitcount(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitcount(byte[] key, long start, long end) {
    Span span = TracingHelper.buildSpan("Redis.Bitcount", key);
    span.setAttribute("start", start);
    span.setAttribute("end", end);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitcount(key, start, end);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
    Span span = TracingHelper.buildSpan("Redis.Bitop");
    span.setAttribute("destKey", Arrays.toString(destKey));
    span.setAttribute("srcKeys", TracingHelper.toString(srcKeys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitop(op, destKey, srcKeys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] dump(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Dump", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.dump(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String restore(byte[] key, int ttl, byte[] serializedValue) {
    Span span = TracingHelper.buildSpan("Redis.Restore", key);
    span.setAttribute("ttl", ttl);
    span.setAttribute("serializedValue", Arrays.toString(serializedValue));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.restore(key, ttl, serializedValue);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String restoreReplace(byte[] key, int ttl, byte[] serializedValue) {
    Span span = TracingHelper.buildSpan("Redis.RestoreReplace", key);
    span.setAttribute("ttl", ttl);
    span.setAttribute("serializedValue", Arrays.toString(serializedValue));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.restoreReplace(key, ttl, serializedValue);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pexpire(byte[] key, long milliseconds) {
    Span span = TracingHelper.buildSpan("Redis.Pexpire", key);
    span.setAttribute("milliseconds", milliseconds);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pexpire(key, milliseconds);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
    Span span = TracingHelper.buildSpan("Redis.PexpireAt", key);
    span.setAttribute("millisecondsTimestamp", millisecondsTimestamp);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pexpireAt(key, millisecondsTimestamp);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pttl(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Pttl", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pttl(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String psetex(byte[] key, long milliseconds, byte[] value) {
    Span span = TracingHelper.buildSpan("Redis.Psetex", key);
    span.setAttribute("milliseconds", milliseconds);
    span.setAttribute("value", Arrays.toString(value));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.psetex(key, milliseconds, value);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientKill(byte[] client) {
    Span span = TracingHelper.buildSpan("Redis.ClientKill");
    span.setAttribute("client", Arrays.toString(client));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientKill(client);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientKill(String ip, int port) {
    Span span = TracingHelper.buildSpan("Redis.ClientKill");
    span.setAttribute("ip", ip);
    span.setAttribute("port", port);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientKill(ip, port);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long clientKill(ClientKillParams params) {
    Span span = TracingHelper.buildSpan("Redis.ClientKill");
    span.setAttribute("ip", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientKill(params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] clientGetnameBinary() {
    Span span = TracingHelper.buildSpan("Redis.ClientGetnameBinary");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientGetnameBinary();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public byte[] clientListBinary() {
    Span span = TracingHelper.buildSpan("Redis.ClientListBinary");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientListBinary();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientSetname(byte[] name) {
    Span span = TracingHelper.buildSpan("Redis.ClientSetname");
    span.setAttribute("name", Arrays.toString(name));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientSetname(name);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String clientPause(long timeout) {
    Span span = TracingHelper.buildSpan("Redis.ClientPause");
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.clientPause(timeout);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<String> time() {
    Span span = TracingHelper.buildSpan("Redis.Time");
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.time();
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String migrate(String host, int port, byte[] key, int destinationDb, int timeout) {
    Span span = TracingHelper.buildSpan("Redis.Migrate", key);
    span.setAttribute("host", host);
    span.setAttribute("port", port);
    span.setAttribute("destinationDb", destinationDb);
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.migrate(host, port, key, destinationDb, timeout);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String migrate(
      String host, int port, int destinationDB, int timeout, MigrateParams params, byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Migrate", keys);
    span.setAttribute("host", host);
    span.setAttribute("port", port);
    span.setAttribute("destinationDB", destinationDB);
    span.setAttribute("timeout", timeout);
    span.setAttribute("params", TracingHelper.toString(params.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.migrate(host, port, destinationDB, timeout, params, keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long waitReplicas(int replicas, long timeout) {
    Span span = TracingHelper.buildSpan("Redis.WaitReplicas");
    span.setAttribute("replicas", replicas);
    span.setAttribute("timeout", timeout);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.waitReplicas(replicas, timeout);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pfadd(byte[] key, byte[]... elements) {
    Span span = TracingHelper.buildSpan("Redis.Pfadd", key);
    span.setAttribute("elements", TracingHelper.toString(elements));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfadd(key, elements);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public long pfcount(byte[] key) {
    Span span = TracingHelper.buildSpan("Redis.Pfcount", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfcount(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public String pfmerge(byte[] destkey, byte[]... sourcekeys) {
    Span span = TracingHelper.buildSpan("Redis.Pfmerge");
    span.setAttribute("destkey", Arrays.toString(destkey));
    span.setAttribute("sourcekeys", TracingHelper.toString(sourcekeys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfmerge(destkey, sourcekeys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long pfcount(byte[]... keys) {
    Span span = TracingHelper.buildSpan("Redis.Pfcount");
    span.setAttribute("keys", TracingHelper.toString(keys));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.pfcount(keys);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<byte[]> scan(byte[] cursor) {
    Span span = TracingHelper.buildSpan("Redis.Scan");
    span.setAttribute("cursor", Arrays.toString(cursor));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scan(cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<byte[]> scan(byte[] cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Scan");
    span.setAttribute("cursor", Arrays.toString(cursor));
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.scan(cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
    Span span = TracingHelper.buildSpan("Redis.Hscan", key);
    span.setAttribute("cursor", Arrays.toString(cursor));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hscan(key, cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Hscan", key);
    span.setAttribute("cursor", Arrays.toString(cursor));
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hscan(key, cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
    Span span = TracingHelper.buildSpan("Redis.Sscan", key);
    span.setAttribute("cursor", Arrays.toString(cursor));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sscan(key, cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Sscan", key);
    span.setAttribute("cursor", Arrays.toString(cursor));
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.sscan(key, cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
    Span span = TracingHelper.buildSpan("Redis.Zscan", key);
    span.setAttribute("cursor", Arrays.toString(cursor));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zscan(key, cursor);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
    Span span = TracingHelper.buildSpan("Redis.Zscan", key);
    span.setAttribute("cursor", Arrays.toString(cursor));
    span.setAttribute("params", TracingHelper.toString(params.getParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.zscan(key, cursor, params);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
    Span span = TracingHelper.buildSpan("Redis.Geoadd", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("member", Arrays.toString(member));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geoadd(key, longitude, latitude, member);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
    Span span = TracingHelper.buildSpan("Redis.Geoadd", key);
    span.setAttribute("memberCoordinateMap", TracingHelper.toStringMapGeneric(memberCoordinateMap));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geoadd(key, memberCoordinateMap);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double geodist(byte[] key, byte[] member1, byte[] member2) {
    Span span = TracingHelper.buildSpan("Redis.Geodist", key);
    span.setAttribute("member1", Arrays.toString(member1));
    span.setAttribute("member2", Arrays.toString(member2));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geodist(key, member1, member2);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.Geodist", key);
    span.setAttribute("member1", Arrays.toString(member1));
    span.setAttribute("member2", Arrays.toString(member2));
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geodist(key, member1, member2, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<byte[]> geohash(byte[] key, byte[]... members) {
    Span span = TracingHelper.buildSpan("Redis.Geohash", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geohash(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
    Span span = TracingHelper.buildSpan("Redis.Geopos", key);
    span.setAttribute("members", Arrays.toString(members));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.geopos(key, members);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadius(
      byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.Georadius", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadius(key, longitude, latitude, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusReadonly(
      byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusReadonly", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusReadonly(key, longitude, latitude, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadius(
      byte[] key,
      double longitude,
      double latitude,
      double radius,
      GeoUnit unit,
      GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.Georadius", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadius(key, longitude, latitude, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusReadonly(
      byte[] key,
      double longitude,
      double latitude,
      double radius,
      GeoUnit unit,
      GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusReadonly", key);
    span.setAttribute("longitude", longitude);
    span.setAttribute("latitude", latitude);
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusReadonly(key, longitude, latitude, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMember(
      byte[] key, byte[] member, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMember", key);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMember(key, member, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMemberReadonly(
      byte[] key, byte[] member, double radius, GeoUnit unit) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMemberReadonly", key);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMemberReadonly(key, member, radius, unit);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMember(
      byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMember", key);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMember(key, member, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMemberReadonly(
      byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
    Span span = TracingHelper.buildSpan("Redis.GeoradiusByMemberReadonly", key);
    span.setAttribute("member", Arrays.toString(member));
    span.setAttribute("radius", radius);
    span.setAttribute("unit", unit.name());
    span.setAttribute("param", TracingHelper.toString(param.getByteParams()));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.georadiusByMemberReadonly(key, member, radius, unit, param);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public List<Long> bitfield(byte[] key, byte[]... arguments) {
    Span span = TracingHelper.buildSpan("Redis.Bitfield", key);
    span.setAttribute("arguments", Arrays.toString(arguments));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.bitfield(key, arguments);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  @Override
  public Long hstrlen(byte[] key, byte[] field) {
    Span span = TracingHelper.buildSpan("Redis.Hstrlen", key);
    span.setAttribute("field", Arrays.toString(field));
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.hstrlen(key, field);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }
}

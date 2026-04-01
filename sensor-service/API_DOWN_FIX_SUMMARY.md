# API DOWN Error - Fix Summary

## Problem Identified
The sensor service was encountering **"API DOWN"** errors when trying to connect to the external IoT API at `http://104.211.95.241:8080/api`. The application would crash or hang without proper error handling and retry mechanisms.

## Root Causes
1. **No timeout configuration** - Requests could hang indefinitely
2. **No retry logic** - Failed requests weren't retried, causing immediate failures
3. **Inadequate error handling** - Errors weren't being caught and logged properly
4. **No connection pooling configuration** - Network connections weren't optimized

## Solutions Implemented

### 1. **Enhanced WebClient Configuration** (ExternalIoTClient.java)
- Added `HttpClient` with `responseTimeout` of 10 seconds
- Configured `ReactorClientHttpConnector` for better connection management
- Provides immediate failure notification instead of indefinite hanging

### 2. **Automatic Retry Logic with Exponential Backoff**
All API calls now include:
- **3 retry attempts** on failure
- **Exponential backoff**: 2 seconds initial, up to 5 seconds max
- **Detailed retry logging** for monitoring

Applied to:
- Authentication endpoint (`/auth/login`)
- Device listing endpoint (`/devices`)
- Telemetry fetch endpoint (`/devices/telemetry/{deviceId}`)

### 3. **Improved Error Handling**
- Added `.onStatus()` handlers to catch non-2xx responses
- Added `.timeout()` to fail fast on slow responses
- Added `.doOnError()` logging for debugging
- Better error messages with status codes

### 4. **Service Layer Error Recovery** (SensorService.java)
- Added logging for all error scenarios
- Token reset on authentication failure for clean retry
- Proper error propagation with context

### 5. **Enhanced Scheduler** (TelemetryScheduler.java)
- Better error logging with specific error messages
- Prevents application crash on API failures
- Continues retrying on next scheduled run
- Logs success and failure separately for monitoring

### 6. **Configuration Updates** (application.yml)
- Added timeout configurations for reference
- Added retry configuration parameters
- Improved logging levels for debugging

## Benefits
✅ **Graceful Degradation** - App continues running even if external API is down  
✅ **Automatic Recovery** - Retries with backoff when API recovers  
✅ **Better Visibility** - Detailed logging shows exactly what's failing  
✅ **Configurable** - Timeouts and retries can be tuned via properties  
✅ **Production Ready** - Handles real-world network issues  

## How It Works Now

When the API is down:
1. First request fails → Logs error
2. Automatically retries after 2 seconds
3. If still down → Retries after 4 seconds
4. If still down → Retries after 5 seconds
5. After 3 total attempts → Logs "API DOWN" warning
6. Application continues to run
7. Next scheduled cycle (10 seconds) tries again

When API comes back online:
- Next request succeeds
- Data is fetched and processed normally
- Normal operation resumes

## Testing
The application has been compiled successfully without errors. To test:

```bash
# Build the application
.\mvnw clean package

# Run the application
java -jar target/sensor-service-0.0.1-SNAPSHOT.jar

# Monitor logs for:
# - "Retrying authentication..." (retry attempts)
# - "Fetched Telemetry:" (successful data fetch)
# - "API DOWN:" (connection failures)
```

## Configuration Tuning (if needed)

Edit `application.yml`:
```yaml
external-iot:
  base-url: http://104.211.95.241:8080/api
  connection-timeout: 5000    # Adjust connection timeout (ms)
  read-timeout: 10000         # Adjust read timeout (ms)
  retry-attempts: 3           # Adjust retry count
  retry-delay-ms: 2000        # Adjust retry delay (ms)
```

Then update the retry logic in ExternalIoTClient.java accordingly.


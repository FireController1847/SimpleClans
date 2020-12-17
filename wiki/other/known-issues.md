---
description: List of the plugin's known issues and possible solutions.
---

# Known issues

#### MySQL \#1366 - Incorrect string value

This error happens when you try to insert characters that MySQL's current encoding doesn't support.

**Solution:** change MySQL's encoding to `utf8mb4`.  
1. Open MySQL's `my.cnf`.  
2. Add these configurations, save and restart MySQL:

```text
[mysql]
default-character-set=utf8mb4
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
```

#### KDR is not counted

Players report that their KDR is not changing.

**Solution:** deny the permission `simpleclans.other.kdr-exempt`. In some permission plugins, just add a `-` before the node. Others accept the `false` value.


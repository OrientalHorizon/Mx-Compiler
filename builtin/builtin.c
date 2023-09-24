#define bool _Bool

typedef unsigned size_t;

int printf(const char *pattern, ...);
int sprintf(char *dest, const char *pattern, ...);
int scanf(const char *pattern, ...);
int sscanf(const char *src, const char *pattern, ...);
size_t strlen(const char *str);
int strcmp(const char *s1, const char *s2);
void *memcpy(void *dest, const void *src, size_t n);
void *malloc(size_t n);
char *strcpy(char *s1, const char *s2);
char *strcat(char *s1, const char *s2);

void print(char* s) {
    printf("%s", s);
}

void println(char *s) {
    printf("%s\n", s);
}

void printInt(int n) {
    printf("%d", n);
}

void printlnInt(int n) {
    printf("%d\n", n);
}

char* getString() {
    char *str = malloc(1 << 8);
    scanf("%s", str);
    return str;
}

char* toString(int n) {
    char *str = malloc(1 << 4);
    sprintf(str, "%d", n);
    return str;
}

int getInt() {
    int n;
    scanf("%d", &n);
    return n;
}

int _string_length(char *s) {
    return strlen(s);
}

char* _string_substring(char *s, int l, int r) {
    char *ret = malloc(r - l + 1);
    for (int i = l; i < r; ++i) {
        ret[i - l] = s[i];
    }
    ret[r - l] = '\0';
    return ret;
}

int _string_parseInt(char *s) {
    int n;
    sscanf(s, "%d", &n);
    return n;
}

int _string_ord(char *s, int n) {
    return s[n];
}

char *_string_add(char *s, char *t) {
    int size1 = strlen(s);
    int size2 = strlen(t);
    char *ret = malloc(size1 + size2 + 1);
    strcpy(ret, s);
    strcat(ret, t);
    return ret;
}

bool _string_lt(char *s, char *t) {
    return strcmp(s, t) < 0;
}

bool _string_le(char *s, char *t) {
    return strcmp(s, t) <= 0;
}

bool _string_gt(char *s, char *t) {
    return strcmp(s, t) > 0;
}

bool _string_ge(char *s, char *t) {
    return strcmp(s, t) >= 0;
}

bool _string_eq(char *s, char *t) {
    return strcmp(s, t) == 0;
}

bool _string_ne(char *s, char *t) {
    return strcmp(s, t) != 0;
}

int _array_size(void *s) {
    return ((int *)s)[-1];
}

void *_new_array(int bytes, int size) {
    int *ret = malloc(bytes);
    ret[0] = size;
    return ret + 1;
}
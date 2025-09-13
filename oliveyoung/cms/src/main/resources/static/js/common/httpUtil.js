const httpUtil = {
    loadingDiv: '<div id="loadingDiv" style="position:fixed; left:0; top:0; z-index:9999; width:100%; height:100%; background:#000; opacity:0.6;"></div>',

    _showLoading() {
        if (!this.$loadDiv) {
            this.$loadDiv = document.createElement('div');
            this.$loadDiv.innerHTML = this.loadingHtml;
            document.body.appendChild(this.$loadDiv.firstChild);
        }
        this.$loadDiv.style.display = 'block';
    },

    _hideLoading() {
        if (this.$loadDiv) {
            this.$loadDiv.style.display = 'none';
        }
    },

    /**
     * GET 요청 (query string 자동 처리)
     * @param {string} url
     * @param {object} [params] - 쿼리 파라미터 객체
     * @param {object} [options] - 추가 옵션 (loading, timeout, headers, withCredentials, responseType)
     * @returns {Promise}
     */
    get(url, params = {}, options = {}) {
        const query = new URLSearchParams(params).toString();
        const fullUrl = query ? `${url}${url.includes('?') ? '&' : '?'}${query}` : url;
        return this._fetch(fullUrl, { method: 'GET', ...options });
    },

    /**
     * POST 요청 (JSON body 자동 처리)
     * @param {string} url
     * @param {object|string} [data] - POST 요청 데이터(JSON 또는 string)
     * @param {object} [options] - 추가 옵션 (loading, timeout, headers, withCredentials, responseType)
     * @returns {Promise}
     */
    post(url, data = null, options = {}) {
        let body = data;
        const headers = options.headers ? { ...options.headers } : {};

        if (data && typeof data === 'object' && !(data instanceof FormData) && !(typeof data === 'string')) {
            body = JSON.stringify(data);
            if (!headers['Content-Type']) {
                headers['Content-Type'] = 'application/json;charset=UTF-8';
            }
        }

        return this._fetch(url, { method: 'POST', body, headers, ...options });
    },

    /**
     * PATCH 요청 (JSON body 자동 처리)
     * @param {string} url
     * @param {object|string} [data] - POST 요청 데이터(JSON 또는 string)
     * @param {object} [options] - 추가 옵션 (loading, timeout, headers, withCredentials, responseType)
     * @returns {Promise}
     */
    patch(url, data = null, options = {}) {
        let body = data;
        const headers = options.headers ? { ...options.headers } : {};

        if (data && typeof data === 'object' && !(data instanceof FormData) && !(typeof data === 'string')) {
            body = JSON.stringify(data);
            if (!headers['Content-Type']) {
                headers['Content-Type'] = 'application/json;charset=UTF-8';
            }
        }

        return this._fetch(url, { method: 'PATCH', body, headers, ...options });
    },

    /**
     * PUT 요청 (JSON body 자동 처리)
     * @param {string} url
     * @param {object|string} [data] - POST 요청 데이터(JSON 또는 string)
     * @param {object} [options] - 추가 옵션 (loading, timeout, headers, withCredentials, responseType)
     * @returns {Promise}
     */
    put(url, data = null, options = {}) {
        let body = data;
        const headers = options.headers ? { ...options.headers } : {};

        if (data && typeof data === 'object' && !(data instanceof FormData) && !(typeof data === 'string')) {
            body = JSON.stringify(data);
            if (!headers['Content-Type']) {
                headers['Content-Type'] = 'application/json;charset=UTF-8';
            }
        }

        return this._fetch(url, { method: 'PUT', body, headers, ...options });
    },

    /**
     * DELETE 요청 (JSON body 자동 처리)
     * @param {string} url
     * @param {object|string} [data] - POST 요청 데이터(JSON 또는 string)
     * @param {object} [options] - 추가 옵션 (loading, timeout, headers, withCredentials, responseType)
     * @returns {Promise}
     */
    delete(url, data = null, options = {}) {
        let body = data;
        const headers = options.headers ? { ...options.headers } : {};

        if (data && typeof data === 'object' && !(data instanceof FormData) && !(typeof data === 'string')) {
            body = JSON.stringify(data);
            if (!headers['Content-Type']) {
                headers['Content-Type'] = 'application/json;charset=UTF-8';
            }
        }

        return this._fetch(url, { method: 'DELETE', body, headers, ...options });
    },

    /**
     * 파일 업로드 (multipart/form-data)
     * @param {string} url
     * @param {object} data - 일반 필드 key-value 객체
     * @param {object} files - 파일 필드 key: input name, value: File | File[] | Blob | Blob[]
     * @param {object} [options] - 추가 옵션 (loading, timeout, headers, withCredentials)
     * @returns {Promise}
     */
    upload(url, data = {}, files = {}, options = {}) {
        if (!url) return Promise.reject(new Error('URL이 필요합니다.'));

        const formData = new FormData();

        for (const key in data) {
            if (Object.prototype.hasOwnProperty.call(data, key)) {
                formData.append(key, data[key]);
            }
        }

        for (const key in files) {
            if (Object.prototype.hasOwnProperty.call(files, key)) {
                const fileOrFiles = files[key];
                if (Array.isArray(fileOrFiles)) {
                    fileOrFiles.forEach(file => formData.append(key, file));
                } else {
                    formData.append(key, fileOrFiles);
                }
            }
        }

        const headers = options.headers ? { ...options.headers } : {};
        // multipart/form-data는 브라우저가 Content-Type 자동 지정
        if (headers['Content-Type']) delete headers['Content-Type'];

        return this._fetch(url, {
            method: 'POST',
            body: formData,
            headers,
            ...options,
        });
    },

    /**
     * 내부 공통 fetch 처리 함수
     * @param {string} url
     * @param {object} options
     * @returns {Promise}
     */
    _fetch(url, options) {
        const defaults = {
            loading: false,
            timeout: 10000,                // 10초 타임아웃
            withCredentials: true,         // 쿠키 포함
            responseType: 'json',
            headers: {},
        };
        const config = { ...defaults, ...options };

        const fetchOptions = {
            method: config.method || 'GET',
            headers: { ...config.headers },
            credentials: config.withCredentials ? 'include' : 'same-origin',
            body: config.body,
        };

        const controller = new AbortController();
        fetchOptions.signal = controller.signal;

        if (config.loading) this._showLoading();

        let timeoutId;
        if (config.timeout > 0) {
            timeoutId = setTimeout(() => controller.abort(), config.timeout);
        }

        return fetch(url, fetchOptions)
            .then(response => {
                if (config.loading) this._hideLoading();

                if (!response.ok) {
                    if (response.status === 401 || response.status === 440) {
                        alert('세션이 만료되었습니다. 다시 로그인해주세요.');
                        location.href = '/login';
                    }
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                switch (config.responseType) {
                    case 'json':
                        return response.json();
                    case 'text':
                        return response.text();
                    case 'blob':
                        return response.blob();
                    default:
                        return response.text();
                }
            })
            .finally(() => {
                if (config.loading) this._hideLoading();
                if (timeoutId) clearTimeout(timeoutId);
            });
    },
};

/*
    httpUtil.post('/api/products/create',
      { name: '신상품', price: 15000 },
      {
        loading: true,                 // 로딩 UI 표시
        timeout: 10000,                // 10초 타임아웃
        withCredentials: true,         // 쿠키 포함
        headers: { 'X-Requested-With': 'XMLHttpRequest' }, // 커스텀 헤더 추가
        responseType: 'json'           // JSON 응답 예상
      }
    ).then(data => {
      console.log('POST 성공 - JSON 응답:', data);
    }).catch(err => {
      console.error('POST 실패:', err);
    });
 */
let WebGLStarter = {
    main: () => {
        const canvas = $('#glCanvas').get(0);
        const gl = WebGLStarter.getContext(canvas);

        const vsSource = `
            attribute vec4 aVertexPosition;

            uniform mat4 uModelViewMatrix;
            uniform mat4 uProjectionMatrix;

            void main() {
              gl_Position = uProjectionMatrix * uModelViewMatrix * aVertexPosition;
            }
          `;

        const fsSource = `
            void main() {
              gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
            }
          `;

        const shaderProgram = WebGLStarter.initShaders(gl, vsSource, fsSource);

        const programInfo = {
            program: shaderProgram,
            attribLocations: {
                vertexPosition: gl.getAttribLocation(shaderProgram, 'aVertexPosition'),
            },
            uniformLocations: {
                projectionMatrix: gl.getUniformLocation(shaderProgram, 'uProjectionMatrix'),
                modelViewMatrix: gl.getUniformLocation(shaderProgram, 'uModelViewMatrix'),
            },
        };

        // gl.clearColor(0.0, 0.0, 0.0, 1.0);
        // gl.clear(gl.COLOR_BUFFER_BIT);
    },

    getContext: (canvas) => {
        const glContext = canvas.getContext("webgl");
        if (!glContext) throw "Unable to initialize WebGL. Your browser or machine may not support it.";
        return glContext;
    },

    /**
     * Initialize shaders
     * @param gl
     * @param vertex
     * @param fragment
     * @returns new shader program according input shaders
     */
    initShaders: (gl, vertex, fragment) => {
        return WebGLStarter.getShaderProgram(
            gl,
            WebGLStarter.loadShader(gl, gl.VERTEX_SHADER, vertex),
            WebGLStarter.loadShader(gl, gl.FRAGMENT_SHADER, fragment)
        )
    },

    /**
     *  Create shader according input data. It uploads the source and compiles it.
     * @param gl
     * @param type
     * @param sourceShader
     * @returns {*} uploaded shader
     */
    loadShader: (gl, type, sourceShader) => {
        const shader = gl.createShader(type);
        gl.shaderSource(shader, sourceShader);
        gl.compileShader(shader);

        if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
            gl.deleteShader(shader);
            throw "Shader hasn't been compiled."
        }

        return shader;
    },

    getShaderProgram: (gl, vShader, fShader) => {
        const shaderProgram = gl.createProgram();
        gl.attachShader(shaderProgram, vShader);
        gl.attachShader(shaderProgram, fShader);
        gl.linkProgram(shaderProgram);

        if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
            throw "Link shader program isn't successful"
        }

        return shaderProgram;
    }
};
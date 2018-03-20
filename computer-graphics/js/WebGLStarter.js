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

        WebGLStarter.drawScene(gl, programInfo, WebGLStarter.initBuffers(gl));
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
        );
    },

    drawScene: (gl, info, buffer) => {
        // gl.clearColor(.43, .43, .43, 1.0);
        gl.clearDepth(1.0);
        gl.enable(gl.DEPTH_TEST);
        gl.depthFunc(gl.LEQUAL);

        gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

        const fieldOfView = 45 * Math.PI / 180;   // in radians
        const aspect = gl.canvas.clientWidth / gl.canvas.clientHeight;
        const zNear = 0.1;
        const zFar = 100.0;
        const projectionMatrix = mat4.create();

        mat4.perspective(projectionMatrix,
            fieldOfView,
            aspect,
            zNear,
            zFar);

        const modelViewMatrix = mat4.create();

        mat4.translate(
            modelViewMatrix,
            modelViewMatrix,
            [-0.0, 0.0, -6.0]
        );


        {
            const numComponents = 2;
            const type = gl.FLOAT;
            const normalize = false;
            const stride = 0;
            const offset = 0;
            gl.bindBuffer(gl.ARRAY_BUFFER, buffer.position);
            gl.vertexAttribPointer(
                info.attribLocations.vertexPosition,
                numComponents,
                type,
                normalize,
                stride,
                offset);
            gl.enableVertexAttribArray(
                info.attribLocations.vertexPosition);
        }


        gl.useProgram(info.program);


        gl.uniformMatrix4fv(
            info.uniformLocations.projectionMatrix,
            false,
            projectionMatrix
        );
        gl.uniformMatrix4fv(
            info.uniformLocations.modelViewMatrix,
            false,
            modelViewMatrix
        );

        {
            const offset = 0;
            const vertexCount = 4;
            gl.drawArrays(gl.TRIANGLE_STRIP, offset, vertexCount);
        }
    },

    initBuffers: (gl) => {
        const positionBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);

        const positions = [
            1.0, 1.0,
            -1.0, 1.0,
            1.0, -1.0,
            -1.0, -1.0
        ];

        gl.bufferData(
            gl.ARRAY_BUFFER,
            new Float32Array(positions),
            gl.STATIC_DRAW
        );

        return {
            position: positionBuffer,
        };
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
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

        WebGLStarter.drawScene(gl, WebGLStarter.getProgramInfo(gl, shaderProgram), WebGLStarter.initBuffers(gl));
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

    getProgramInfo: (gl, shaderProgram) => {
        return {
            program: shaderProgram,
            attribLocations: {
                vertexPosition: gl.getAttribLocation(shaderProgram, 'aVertexPosition'),
            },
            uniformLocations: {
                projectionMatrix: gl.getUniformLocation(shaderProgram, 'uProjectionMatrix'),
                modelViewMatrix: gl.getUniformLocation(shaderProgram, 'uModelViewMatrix'),
            },
        };

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
            const numComponents = 3;
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
            const vertexCount = 36;
            const type = gl.UNSIGNED_SHORT;
            const offset = 0;
            gl.drawElements(gl.TRIANGLES, vertexCount, type, offset);
        }
    },

    initBuffers: (gl) => {
        const positionBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
        gl.bufferData(
            gl.ARRAY_BUFFER,
            new Float32Array(Shapes.getCube()),
            gl.STATIC_DRAW
        );

        const indices = [
            0,  1,  2,      0,  2,  3,
            4,  5,  6,      4,  6,  7,
            8,  9,  10,     8,  10, 11,
            12, 13, 14,     12, 14, 15,
            16, 17, 18,     16, 18, 19,
            20, 21, 22,     20, 22, 23,
        ];

        // Now send the element array to GL
        const indexBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, indexBuffer);
        gl.bufferData(gl.ELEMENT_ARRAY_BUFFER,
            new Uint16Array(indices), gl.STATIC_DRAW);

        const faceColors = [
            [1.0,  1.0,  1.0,  1.0],    // Front face: white
            [1.0,  0.0,  0.0,  1.0],    // Back face: red
            [0.0,  1.0,  0.0,  1.0],    // Top face: green
            [0.0,  0.0,  1.0,  1.0],    // Bottom face: blue
            [1.0,  1.0,  0.0,  1.0],    // Right face: yellow
            [1.0,  0.0,  1.0,  1.0],    // Left face: purple
        ];

        // Convert the array of colors into a table for all the vertices.

        let colors = [];

        for (let j = 0; j < faceColors.length; ++j) {
            const c = faceColors[j];
            colors = colors.concat(c, c, c, c);
        }

        const colorBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);

        return {
            position: positionBuffer,
            indices: indexBuffer,
            color: colorBuffer,
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
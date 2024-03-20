module.exports = {
    // Gibt an, welche Dateien Jest testen soll
    testMatch: ['**/__tests__/**/*.js', '**/?(*.)+(spec|test).js'],

    // Gibt an, welche Ordner Jest ignorieren soll
    testPathIgnorePatterns: ['/node_modules/'],

    // Gibt an, dass Jest React-Tests ausführen soll
    preset: 'react-scripts',

    // Gibt an, dass Jest die .jsx-Erweiterung unterstützen soll
    moduleFileExtensions: ['js', 'jsx'],

    // Gibt an, dass Jest auch TypeScript-Dateien verarbeiten soll
    transform: {
        '^.+\\.(js|jsx)?$': 'babel-jest',
    },

    // Gibt an, welche Dateien von der Transformation ausgeschlossen werden sollen
    transformIgnorePatterns: [
        '/node_modules/(?!(axios)/)',
    ],
};

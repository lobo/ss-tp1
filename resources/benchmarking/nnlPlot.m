
	% Plotea un sistema de partículas y los vecinos cercanos de cierta partícula:
	function [] = nnlPlot(source, target)

		% Genera un archivo de partículas aleatorias con el formato <id, x, y, r, neighbours>:
		% java -jar target/tp1-1.0-SNAPSHOT.jar cell 100 0.25 10 1 true 0 <filename>

		clc;

		% Obtener partículas:
		file = fopen(source, 'r');
		header = str2num(fgetl(file));
		content = textscan(file, '%d %f %f %f %s', 'Delimiter', '\n');
		fclose(file);

		% Estructurar información:
		space = 20.0;
		space = header(1, 1);
		Rc = header(1, 2);
		id = content{:, 1};
		x = content{:, 2};
		y = content{:, 3};
		r = content{:, 4};
		neighbours = {};
		for k = 1:size(id, 1)
			neighbours{k, 1} = str2num(char(content{1, 5}(k)));
		end

		% Detectar partícula distinguida y vecinos:
		T = [];
		nnl = [];
		interaction = [];

		for k = 1:size(id, 1)
			if (target == id(k, 1))
				nnl = neighbours{k, 1}';
				T = [target, x(k, 1), y(k, 1), r(k, 1)];
			end
		end

		for (k = 1:size(id, 1))
			if (0 < sum(id(k, 1) == nnl(:, 1)))
				interaction(end + 1, 1:3) = [x(k, 1), y(k, 1), r(k, 1)];
			end
		end

		% Resolution = 1366x768
		display = figure();
		display.addprop('Resolution');
		display.Resolution = get(0, 'ScreenSize');
		display.SizeChangedFcn = @resize;
		display.Name = 'Sistema de Particulas';
		display.NumberTitle = 'off';
		display.Units = 'pixels';

		hold on;

		display.addprop('Space');
		display.Space = space;
		display.addprop('Plots');
		display.Plots = {
			scatter(x, y, r, [0.2, 0.7, 0.9]),
			scatter(T(1, 2), T(1, 3), T(1, 4), [1.0, 0.3, 0.3]),
			scatter(T(1, 2), T(1, 3), T(1, 4) + Rc, [0.7, 0.7, 0.7]),
			scatter(interaction(:, 1), interaction(:, 2), interaction(:, 3), [1.0, 0.8, 0.0])
		};

		for k = 1:size(display.Plots)
			display.Plots{k}.addprop('OriginalSizeData');
			display.Plots{k}.OriginalSizeData = display.Plots{k}.SizeData;
		end

		display.CurrentAxes.PlotBoxAspectRatio = [1 1 1];
		display.CurrentAxes.Units = 'pixels';
		display.CurrentAxes.Title.String = 'Sistema de Particulas + Cell Index Method';
		display.CurrentAxes.Title.FontSize = 16;
		display.CurrentAxes.Title.FontWeight = 'bold';
		display.CurrentAxes.Title.Color = [0, 0, 0];
		display.CurrentAxes.XLabel.String = 'Coordenada - X';
		display.CurrentAxes.XLabel.FontSize = 16;
		display.CurrentAxes.XLabel.FontWeight = 'bold';
		display.CurrentAxes.XLabel.Color = [0, 0, 0];
		display.CurrentAxes.YLabel.String = 'Coordenada - Y';
		display.CurrentAxes.YLabel.FontSize = 16;
		display.CurrentAxes.YLabel.FontWeight = 'bold';
		display.CurrentAxes.YLabel.Color = [0, 0, 0];
		display.CurrentAxes.XGrid = 'on';
		display.CurrentAxes.YGrid = 'on';
		display.CurrentAxes.XTick = 0:1:space;
		display.CurrentAxes.YTick = 0:1:space;
		display.CurrentAxes.XLim = [0 space];
		display.CurrentAxes.YLim = [0 space];
		display.CurrentAxes.addprop('Legend');
		display.CurrentAxes.Legend = legend({
			'Particulas sin Interaccion',
			'Particula Objetivo',
			'Radio de Interaccion',
			'Vecinos'
		});
		display.CurrentAxes.Legend.Units = 'pixels';
		display.CurrentAxes.Legend.Position = [1 1 150 70];

		% Actualizar ahora:
		resize(display);
	end

	function [] = resize(source, event)
		try
			% Tamaño de toda la pantalla:
			% source.Resolution;

			% Tamaño de la ventana completa:
			% source.OuterPosition;

			% Tamaño disponible para todo el plot:
			% source.Position;

			% Tamaño efectivo del plot completo:
			source.CurrentAxes.OuterPosition = [1, 1, source.Position(3:4)];

			% Tamaño para el espacio (reporta mal el 'aspect ratio'):
			% source.CurrentAxes.Position;

			% C es el tamaño de la celda de lado 1 (hay un factor de error):
			C = ceil(source.CurrentAxes.Position(4) / source.Space);
			C = ceil(0.5 * sqrt(2) * C);

			plots = source.Plots;
			for k = 1:size(plots)
				% DiP es el diámetro, en pixels:
				DiP = ceil(2 * C * plots{k}.OriginalSizeData);
				plots{k}.SizeData = DiP .^ 2;
			end
		catch exception
		end
	end

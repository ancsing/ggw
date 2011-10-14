package geogebra.kernel.commands;


import geogebra.kernel.GeoCurveCartesian;
import geogebra.kernel.GeoElement;
import geogebra.kernel.GeoFunction;
import geogebra.kernel.GeoList;
import geogebra.kernel.GeoLocus;
import geogebra.kernel.GeoNumeric;
import geogebra.kernel.GeoPoint;
import geogebra.kernel.GeoText;
import geogebra.kernel.GeoVec3D;
import geogebra.kernel.Kernel;
import geogebra.kernel.arithmetic.Command;
import geogebra.kernel.kernelND.GeoSegmentND;
import geogebra.main.MyError;

/**
 * Length[ <GeoVector> ] Length[ <GeoPoint> ] Length[ <GeoList> ] Victor Franco
 * 18-04-2007: add Length[ <Function>, <Number>, <Number> ] add Length[
 * <Function>, <Point>, <Point> ] add Length[ <Curve>, <Number>, <Number> ] add
 * Length[ <Curve>, <Point>, <Point> ]
 */
public class CmdLength extends CommandProcessor {

	/**
	 * Create new command processor
	 * 
	 * @param kernel
	 *            kernel
	 */
	public CmdLength(Kernel kernel) {
		super(kernel);
	}

	public GeoElement[] process(Command c) throws MyError {
		int n = c.getArgumentNumber();
		boolean[] ok = new boolean[n];
		GeoElement[] arg;

		switch (n) {
		case 1:
			arg = resArgs(c);
			if (arg[0].isGeoVector() || arg[0].isGeoPoint()) {
				GeoElement[] ret = { kernel.Length(c.getLabel(),
						(GeoVec3D) arg[0]) };
				return ret;
			} else if (arg[0].isGeoList()) {
				GeoElement[] ret = { kernel.Length(c.getLabel(),
						(GeoList) arg[0]) };
				return ret;
			} else if (arg[0].isGeoText()) {
				GeoElement[] ret = { kernel.Length(c.getLabel(),
						(GeoText) arg[0]) };
				return ret;
			} else if (arg[0].isGeoLocus()) {
				GeoElement[] ret = { kernel.Length(c.getLabel(),
						(GeoLocus) arg[0]) };
				return ret;
			} else if (arg[0].isGeoSegment()) {
					GeoElement[] ret = { kernel.Length(c.getLabel(),
							(GeoSegmentND) arg[0]) };
					return ret;
				
			} else {
				throw argErr(app, c.getName(), arg[0]);
			}

			// Victor Franco 18-04-2007
		case 3:
			arg = resArgs(c);
			if ((ok[0] = (arg[0].isGeoFunctionable()))
					&& (ok[1] = (arg[1].isGeoNumeric()))
					&& (ok[2] = (arg[2].isGeoNumeric()))) {
				GeoElement[] ret = { kernel.FunctionLength(c.getLabel(),
						(GeoFunction) arg[0], (GeoNumeric) arg[1],
						(GeoNumeric) arg[2]) };
				return ret;
			}

			else if ((ok[0] = (arg[0].isGeoFunctionable()))
					&& (ok[1] = (arg[1].isGeoPoint()))
					&& (ok[2] = (arg[2].isGeoPoint()))) {

				GeoElement[] ret = { kernel.FunctionLength2Points(c.getLabel(),
						(GeoFunction) arg[0], (GeoPoint) arg[1],
						(GeoPoint) arg[2]) };
				return ret;
			}

			else if ((ok[0] = (arg[0].isGeoCurveCartesian()))
					&& (ok[1] = (arg[1].isGeoNumeric()))
					&& (ok[2] = (arg[2].isGeoNumeric()))) {

				GeoElement[] ret = { kernel.CurveLength(c.getLabel(),
						(GeoCurveCartesian) arg[0], (GeoNumeric) arg[1],
						(GeoNumeric) arg[2]) };
				return ret;

			}

			else if ((ok[0] = (arg[0].isGeoCurveCartesian()))
					&& (ok[1] = (arg[1].isGeoPoint()))
					&& (ok[2] = (arg[2].isGeoPoint()))) {

				GeoElement[] ret = { kernel.CurveLength2Points(c.getLabel(),
						(GeoCurveCartesian) arg[0], (GeoPoint) arg[1],
						(GeoPoint) arg[2]) };
				return ret;
			}

			else {

				if (!ok[0])
					throw argErr(app, c.getName(), arg[0]);
				if (!ok[1])
					throw argErr(app, c.getName(), arg[1]);
				else
					throw argErr(app, c.getName(), arg[2]);
			}

			// Victor Franco 18-04-2007 (end)
		default:
			throw argNumErr(app, "Length", n);
		}
	}
}

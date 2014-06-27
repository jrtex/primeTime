/*
 * Created on 01.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/LocationRemapper.java,v 1.1 2005/03/01 14:18:41 wova Exp $
 */
package de.vahrson.pt;

import java.util.*;

import de.vahrson.seq.Region;

/**
 * @author wova
 * 
 * Primer positions in ORFs are relative to the respective ORF. This class maps
 * these positions to the sequence the ORFs were extracted from originally.
 */
public class LocationRemapper {
	private List fRegions;

	public LocationRemapper(List regions) {
		fRegions = regions;
	}

	public int mapPosition(int pos, boolean directStrand) {
		Region r = findRegion(pos);
		int answer;
		if (directStrand) {
			answer = r.getLeftPosition().getPosition()  + pos - 1 - lengthUpToRegion(r);
		}
		else { // rev. compl.
			answer = r.getRightPosition().getPosition() - pos + 1 + lengthUpToRegion(r);
		}
		return answer;
	}

	/**
	 * find the Region within wich pos lies
	 * assumes correct ordering of regions
	 * 
	 * @param pos
	 * @param directStrand
	 * @return
	 */
	Region findRegion(int pos) {
		int sum = 0;
		Region found = null;
		for (Iterator i = fRegions.iterator(); found == null && i.hasNext();) {
			Region r = (Region) i.next();
			sum += r.length();
			if (sum >= pos) {
				found = r;
			}
		}
		return found;
	}
	/**
	 * Answer the accumulated lengths of regions up to, and not including, region
	 * @param r
	 * @return
	 */
	int lengthUpToRegion(Region region) {
		int answer= 0;
		boolean goOn= true;
		for (Iterator i = fRegions.iterator(); goOn && i.hasNext();) {
			Region r = (Region) i.next();
			if (r != region) {
				answer+= r.length();
			}
			else {
				goOn= false;
			}
		}
		return answer;
	}
}